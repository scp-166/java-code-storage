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
public class UnlockConditionManager implements ParseStringStr{
	private static class Holder {
		public static UnlockConditionManager instance = new UnlockConditionManager();
	}

	public static UnlockConditionManager getInstance() {
		return Holder.instance;
	}

	/**
	 * switch case 需要非动态变量，直接缓存
	 */
	private static final Map<Integer, UnlockConditionEnum> unlockConditionEnumCache = new HashMap<>();

	private UnlockConditionManager() {
		for (UnlockConditionEnum value : UnlockConditionEnum.values()) {
			unlockConditionEnumCache.put(value.getConditionId(), value);
		}
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
		List<UnlockCondition> andGroups;

		UnlockCondition tempCondition;

		for (List<String> sourceAndGroups : stringConditionGroups) {
			andGroups = new ArrayList<>(sourceAndGroups.size());
			for (String conditionItem : sourceAndGroups) {
				String[] conditionArr = StringUtils.split(conditionItem, ",");
				if (Objects.isNull(conditionArr)) {
					continue;
				}
				tempCondition = new UnlockCondition(Integer.parseInt(conditionArr[0]), conditionArr[1]);
				andGroups.add(tempCondition);
			}
			orGroups.add(andGroups);
		}
		return orGroups;
	}

	/**
	 * 检查是否满足解锁条件组条件组
	 *
	 * @param userId     用户id
	 * @param entranceId 入口id
	 * @param orGroups   条件组
	 * @return 成功返回0; 如果条件组都失败，则返回第一个条件的 id; 有其他异常情况，则返回 -1() 和 -2(未找到预设条件)
	 */
	public int checkUnlockConditionComplete(long userId, int entranceId, List<List<UnlockCondition>> orGroups) {
		// 首个未能完成的条件id
		int firstInCompleteConditionId = -1;
		// and 关系组返回值
		int andRet;

		for (List<UnlockCondition> andGroups : orGroups) {
			andRet = 0;

			// 开始鉴别内层 and 组
			for (UnlockCondition andItem : andGroups) {
				// 非预设的条件
				UnlockConditionEnum anEnum = unlockConditionEnumCache.get(andItem.getConditionId());
				if (Objects.isNull(anEnum)) {
				    andRet = -1;
					// todo {@author lyl} error logger
				} else {
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
				}

				// 且关系组有一个失败，则整个组失败
				if (andRet != 0) {
					break;
				}
			}

			// 当前外层一个or组完成，则快速成功; 并且判断记录第一个失败的条件id
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
        String conditionStr = "1,50;2,500";  // 1
		// String conditionStr = "1,5|2,5000";  // 0
		List<List<UnlockCondition>> lists = UnlockConditionManager.getInstance().parse2ConditionGroup(conditionStr);
		System.out.println(lists);
		System.out.println(UnlockConditionManager.getInstance().checkUnlockConditionComplete(910002L, 20, lists));
	}
}
