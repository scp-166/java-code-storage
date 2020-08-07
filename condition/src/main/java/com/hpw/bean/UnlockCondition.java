package com.hpw.bean;

/**
 * 单个条件组合
 */
public class UnlockCondition {
    private Integer conditionId;
    private String expectValue;

    public UnlockCondition() {
    }

    public UnlockCondition(Integer conditionId, String expectValue) {
        this.conditionId = conditionId;
        this.expectValue = expectValue;
    }

    public Integer getConditionId() {
        return conditionId;
    }

    public void setConditionId(Integer conditionId) {
        this.conditionId = conditionId;
    }

    public String getExpectValue() {
        return expectValue;
    }

    public void setExpectValue(String expectValue) {
        this.expectValue = expectValue;
    }

    @Override
    public String toString() {
        return "Condition{" +
                "conditionId=" + conditionId +
                ", expectValue='" + expectValue + '\'' +
                '}';
    }
}