package com.hpw.strategy.impl;

import com.hpw.bean.UnlockCondition;
import com.hpw.strategy.UnlockConditionStrategy;

/**
 * 金额限定
 */
public class MoneyLimitStrategyImpl implements UnlockConditionStrategy {
    @Override
    public int isMeetCondition(long userId, int entranceId, UnlockCondition unlockCondition) {
        Integer conditionId = unlockCondition.getConditionId();
        // todo {@author lyl} 返回用户持有 金币 是否达到条件
        int currentMoney = 1000;
        if (Integer.parseInt(unlockCondition.getExpectValue()) < currentMoney ) {
            return 0;
        }
        return conditionId;
    }
}
