package com.hpw.myenum;

public enum UnlockConditionEnum {
    VIP_ATTACH(1, "vip要达到指定等级，即 >="),
    MONEY_ATTACH(2, "金币要达到指定数量，即 >="),
    ITEM_UNLOCK(100, "使用指定道具"),
    ;
    private Integer conditionId;
    private String desc;

    UnlockConditionEnum(Integer conditionId, String desc) {
        this.conditionId = conditionId;
        this.desc = desc;
    }

    public Integer getConditionId() {
        return conditionId;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return "UnlockConditionEnum{" +
                "condition=" + conditionId +
                ", desc='" + desc + '\'' +
                '}';
    }
}
