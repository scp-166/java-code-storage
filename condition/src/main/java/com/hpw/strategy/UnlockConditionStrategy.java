package com.hpw.strategy;

import com.hpw.bean.UnlockCondition;

/**
 * 试一下策略模式
 */
public interface UnlockConditionStrategy {
    /**
     * 是否匹配完成条件
     * @return 完成返回0，不完成则返回对应的 conditionId
     */
    int isMeetCondition(long userId, int entranceId, UnlockCondition unlockCondition);
}
