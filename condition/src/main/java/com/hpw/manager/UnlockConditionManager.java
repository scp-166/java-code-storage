package com.hpw.manager;

import com.hpw.bean.ExtractUnlockCost;
import com.hpw.bean.UnlockCondition;
import com.hpw.myenum.UnlockConditionEnum;
import com.hpw.strategy.impl.MoneyLimitStrategyImpl;
import com.hpw.strategy.impl.VipLimitStrategyImpl;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 解锁条件管理
 */
public class UnlockConditionManager {
    private UnlockConditionManager() {
        for (UnlockConditionEnum value : UnlockConditionEnum.values()) {
            unlockConditionEnumCache.put(value.getConditionId(), value);
        }
    }

    /**
     * switch case 需要非动态变量，直接缓存
     */
    private static final Map<Integer, UnlockConditionEnum> unlockConditionEnumCache = new HashMap<>();

    private static class Holder {
        public static UnlockConditionManager instance = new UnlockConditionManager();
    }

    public static UnlockConditionManager getInstance() {
        return Holder.instance;
    }

    /**
     * 转化为解锁条件组
     *
     * @param unlockConditionStr 条件组字符串
     * @return 双层 list, 最内层是且关系，最外层是或关系，最小单位为 {@link UnlockCondition}
     */
    public List<List<UnlockCondition>> parse2ConditionGroup(String unlockConditionStr) {
        if (StringUtils.isEmpty(unlockConditionStr)) {
            return Collections.emptyList();
        }


        char ch;
        int operationStartIndex = 0;

        List<List<UnlockCondition>> orGroups = new ArrayList<>(3);
        List<UnlockCondition> andGroups = new ArrayList<>(3);

        UnlockCondition tempCondition;

        for (int i = 0, size = unlockConditionStr.length(); i < size; i++) {
            ch = unlockConditionStr.charAt(i);

            if (ch == ';' || ch == '|') {
                String substring = unlockConditionStr.substring(operationStartIndex, i);
                String[] conditionArr = StringUtils.split(substring, ",");
                if (Objects.isNull(conditionArr)) {
                    continue;
                }
                tempCondition = new UnlockCondition(Integer.parseInt(conditionArr[0]), conditionArr[1]);
                andGroups.add(tempCondition);
                operationStartIndex = i + 1;
            }

            if (i == size - 1) {
                // 最后一行需要全部填充
                String substring = unlockConditionStr.substring(operationStartIndex, i + 1);
                String[] conditionArr = StringUtils.split(substring, ",");
                if (Objects.isNull(conditionArr)) {
                    continue;
                }
                tempCondition = new UnlockCondition(Integer.parseInt(conditionArr[0]), conditionArr[1]);
                andGroups.add(tempCondition);
                operationStartIndex = i + 1;
            }

            // 遇到 | 或者 末尾时，把之前的内层and组添加到or组
            if (ch == '|' || i == size - 1) {
                orGroups.add(andGroups);
                andGroups = new ArrayList<>(3);
            }
        }
        return orGroups;
    }

    /**
     * 转化为额外解锁花费组
     *
     * @param extraUnlockCostStr 条件组字符串
     * @return 双层 list, 最内层是且关系，最外层是或关系，最小单位为 {@link ExtractUnlockCost}
     */
    public List<List<ExtractUnlockCost>> parse2CostGroup(String extraUnlockCostStr) {
        if (StringUtils.isEmpty(extraUnlockCostStr)) {
            return Collections.emptyList();
        }


        char ch;
        int operationStartIndex = 0;

        List<List<ExtractUnlockCost>> orGroups = new ArrayList<>(3);
        List<ExtractUnlockCost> andGroups = new ArrayList<>(3);

        ExtractUnlockCost tempCost;

        for (int i = 0, size = extraUnlockCostStr.length(); i < size; i++) {
            ch = extraUnlockCostStr.charAt(i);

            if (ch == ';' || ch == '|') {
                String substring = extraUnlockCostStr.substring(operationStartIndex, i);
                String[] conditionArr = StringUtils.split(substring, ",");
                if (Objects.isNull(conditionArr)) {
                    continue;
                }
                tempCost = new ExtractUnlockCost(Integer.parseInt(conditionArr[0]),
                        Integer.parseInt(conditionArr[1]), Integer.parseInt(conditionArr[2]));
                andGroups.add(tempCost);
                operationStartIndex = i + 1;
            }

            if (i == size - 1) {
                // 最后一行需要全部填充
                String substring = extraUnlockCostStr.substring(operationStartIndex, i + 1);
                String[] conditionArr = StringUtils.split(substring, ",");
                if (Objects.isNull(conditionArr)) {
                    continue;
                }
                tempCost = new ExtractUnlockCost(Integer.parseInt(conditionArr[0]),
                        Integer.parseInt(conditionArr[1]), Integer.parseInt(conditionArr[2]));
                andGroups.add(tempCost);
                operationStartIndex = i + 1;
            }

            // 遇到 | 或者 末尾时，把之前的内层and组添加到or组
            if (ch == '|' || i == size - 1) {
                orGroups.add(andGroups);
                andGroups = new ArrayList<>(3);
            }
        }
        return orGroups;
    }


    // todo {@date 2020/08/07}
    public int checkUnlockConditionFailFast(long userId, int entranceId, List<List<UnlockCondition>> orGroups) {
        for (List<UnlockCondition> andGroups : orGroups) {
            int ret = -1;

            for (UnlockCondition andItem : andGroups) {
                UnlockConditionEnum anEnum = unlockConditionEnumCache.get(andItem.getConditionId());
                if (Objects.isNull(anEnum)) {
                    ret = -1;
                    break;
                }
                switch (anEnum) {
                    case VIP_ATTACH:
                        ret = new VipLimitStrategyImpl().isMeetCondition(userId, entranceId, andItem);
                        break;
                    case MONEY_ATTACH:
                        ret = new MoneyLimitStrategyImpl().isMeetCondition(userId, entranceId, andItem);
                        break;
                    default:
                        // todo {@author lyl} error logger
                        ret = -1;
                        break;
                }
            }

            // 当前条件组未完成，则快速失败
            if (ret != 0) {
                return ret;
            }
        }

        return -1;
    }
}
