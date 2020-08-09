package com.hpw.manager;

import com.hpw.bean.ExtractUnlockCost;
import com.hpw.bean.UnlockCondition;
import com.hpw.myenum.UnlockConditionEnum;
import com.hpw.strategy.impl.MoneyLimitStrategyImpl;
import com.hpw.strategy.impl.VipLimitStrategyImpl;
import org.springframework.util.CollectionUtils;
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
	 * 转换为条件组，最小单位不做处理，仅存储
	 *
	 * @param conditionStr 条件组字符串
	 * @return 双层 list，最内层时且关系，最外层时或关系，最小单位为 {@link String}
	 */
	private List<List<String>> parse2StringConditionGroup(String conditionStr) {
		if (StringUtils.isEmpty(conditionStr)) {
			return Collections.emptyList();
		}


		char ch;
		int operationStartIndex = 0;

		List<List<String>> orGroups = new ArrayList<>(3);
		List<String> andGroups = new ArrayList<>(3);

		for (int i = 0, size = conditionStr.length(); i < size; i++) {
			ch = conditionStr.charAt(i);

			if (ch == ';' || ch == '|') {
				String conditionItem = conditionStr.substring(operationStartIndex, i);
				andGroups.add(conditionItem);
				operationStartIndex = i + 1;
			}

			if (i == size - 1) {
				// 最后一行需要全部填充, 不跳过当前字符
				String conditionItem = conditionStr.substring(operationStartIndex, i + 1);
				andGroups.add(conditionItem);
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
	 * 转化为解锁条件组
	 *
	 * @param conditionStr 条件组字符串
	 * @return 双层 list, 最内层是且关系，最外层是或关系，最小单位为 {@link UnlockCondition}
	 */
	public List<List<UnlockCondition>> parse2ConditionGroup(String conditionStr) {
		if (StringUtils.isEmpty(conditionStr)) {
			return Collections.emptyList();
		}

		List<List<String>> stringConditionGroups = parse2StringConditionGroup(conditionStr);

		List<List<UnlockCondition>> orGroups = new ArrayList<>(stringConditionGroups.size());
		List<UnlockCondition> andGroups = new ArrayList<>(3);

		UnlockCondition tempCondition;

		for (List<String> sourceAndGroups : stringConditionGroups) {
			for (String conditionItem : sourceAndGroups) {
				String[] conditionArr = StringUtils.split(conditionItem, ",");
				if (Objects.isNull(conditionArr)) {
					continue;
				}
				tempCondition = new UnlockCondition(Integer.parseInt(conditionArr[0]), conditionArr[1]);
				andGroups.add(tempCondition);
			}
			orGroups.add(andGroups);
			andGroups = new ArrayList<>(3);
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

		List<List<String>> stringConditionGroups = parse2StringConditionGroup(extraUnlockCostStr);

		List<List<ExtractUnlockCost>> orGroups = new ArrayList<>(stringConditionGroups.size());
		List<ExtractUnlockCost> andGroups = new ArrayList<>(3);
		ExtractUnlockCost tempCost;

		for (List<String> sourceAndGroups : stringConditionGroups) {
			for (String conditionItem : sourceAndGroups) {
				String[] conditionArr = StringUtils.split(conditionItem, ",");
				if (Objects.isNull(conditionArr)) {
					continue;
				}
				tempCost = new ExtractUnlockCost(Integer.parseInt(conditionArr[0]),
						Integer.parseInt(conditionArr[1]), Integer.parseInt(conditionArr[2]));
				andGroups.add(tempCost);
			}
			orGroups.add(andGroups);
			andGroups = new ArrayList<>(3);
		}
		return orGroups;
	}


	// todo {@date 2020/08/07}

	/**
	 * 检查是否满足解锁条件组条件组
	 *
	 * @param userId     用户id
	 * @param entranceId 入口id
	 * @param orGroups   条件组
	 * @return 成功返回1; 如果条件组都失败，则返回第一个条件的 id
	 */
	public int checkUnlockConditionComplete(long userId, int entranceId, List<List<UnlockCondition>> orGroups) {
		// 首个未能完成的条件id
		int firstInCompleteConditionId = -1;
		// 且关系组 ret
		int andRet = -1;

		for (List<UnlockCondition> andGroups : orGroups) {
			andRet = -1;

			// 开始鉴别且组
			for (UnlockCondition andItem : andGroups) {
				// 非预设的条件，则跳过
				UnlockConditionEnum anEnum = unlockConditionEnumCache.get(andItem.getConditionId());
				if (Objects.isNull(anEnum)) {
					continue;
				}

				// 根据条件进行对应的判断处理
				switch (anEnum) {
					case VIP_ATTACH:
						andRet = new VipLimitStrategyImpl().isMeetCondition(userId, entranceId, andItem);
						break;
					case MONEY_ATTACH:
						andRet = new MoneyLimitStrategyImpl().isMeetCondition(userId, entranceId, andItem);
						break;
					default:
						// todo {@author lyl} error logger
						andRet = -1;
						break;
				}

				// 且关系组有一个失败，则整个组失败
				if (andRet != 0) {
					break;
				}
			}

			// 当前条件组完成，则快速成功; 或者判断记录第一个失败的组
			if (andRet == 0) {
				return andRet;
			} else {
				if (firstInCompleteConditionId == -1) {
					firstInCompleteConditionId = andRet;
				}
			}
		}

		return firstInCompleteConditionId;
	}

	public static void main(String[] args) {
        // String conditionStr = "1,5;2,5000|1,90";  // 2
        // String conditionStr = "1,5;2,5000";  // 2
        // String conditionStr = "1,50;2,500";  // 1
		String conditionStr = "1,5|2,5000";  // 0
		List<List<UnlockCondition>> lists = UnlockConditionManager.getInstance().parse2ConditionGroup(conditionStr);
		System.out.println(lists);
		System.out.println(UnlockConditionManager.getInstance().checkUnlockConditionComplete(910002L, 20, lists));
	}
}
