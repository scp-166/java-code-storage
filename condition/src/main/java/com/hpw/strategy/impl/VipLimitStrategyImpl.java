package com.hpw.strategy.impl;

import com.hpw.bean.UnlockCondition;
import com.hpw.strategy.UnlockConditionStrategy;

/**
 * vip限定
 */
public class VipLimitStrategyImpl implements UnlockConditionStrategy {
    @Override
    public int isMeetCondition(long userId, int entranceId, UnlockCondition unlockCondition) {
        Integer conditionId = unlockCondition.getConditionId();
        // todo {@author lyl} 返回 vip 是否达到条件
        int currentVip = 10;
        if (Integer.parseInt(unlockCondition.getExpectValue()) < currentVip ) {
            return 0;
        }
        return conditionId;
    }
}
