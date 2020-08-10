package com.hpw.manager;

import com.hpw.bean.ExtractUnlockCost;
import com.hpw.bean.UserEntranceUnlockCost;
import org.springframework.util.StringUtils;

import java.util.*;

public class UserEntranceUnlockCostManager implements ParseStringStr{
    private static class Holder {
        public static UserEntranceUnlockCostManager instance = new UserEntranceUnlockCostManager();
    }

    public static UserEntranceUnlockCostManager getInstance() {
        return Holder.instance;
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

        List<List<String>> stringConditionGroups = parse2StringConditionGroup(extraUnlockCostStr);

        List<List<ExtractUnlockCost>> orGroups = new ArrayList<>(stringConditionGroups.size());
        List<ExtractUnlockCost> andGroups;
        ExtractUnlockCost tempCost;

        for (List<String> sourceAndGroups : stringConditionGroups) {
            andGroups = new ArrayList<>(sourceAndGroups.size());
            for (String conditionItem : sourceAndGroups) {
                String[] conditionArr = conditionItem.split(",");
                if (Objects.isNull(conditionArr)) {
                    continue;
                }
                tempCost = new ExtractUnlockCost(Integer.parseInt(conditionArr[0]),
                        Integer.parseInt(conditionArr[1]), Integer.parseInt(conditionArr[2]));
                andGroups.add(tempCost);
            }
            orGroups.add(andGroups);

        }
        return orGroups;
    }

    public static void main(String[] args) {
        String str = "1,2,3;3,3,3";
        System.out.println(UserEntranceUnlockCostManager.getInstance().parse2CostGroup(str));
        System.out.println(Arrays.toString(StringUtils.split(str, ";")));

        StringTokenizer st = new StringTokenizer(str, "|");
        System.out.println(st.countTokens());
        while (st.hasMoreElements()) {
            System.out.println(st.nextElement());
        }
    }

}
