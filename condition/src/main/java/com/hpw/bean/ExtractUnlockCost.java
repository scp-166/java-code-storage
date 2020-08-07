package com.hpw.bean;

/**
 * 单个花费记录
 */
public class ExtractUnlockCost {
    private Integer id;
    private Integer type;
    private Integer value;

    public ExtractUnlockCost() {
    }

    public ExtractUnlockCost(Integer id, Integer type, Integer value) {
        this.id = id;
        this.type = type;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ExtractUnlockCost{" +
                "id=" + id +
                ", type=" + type +
                ", value=" + value +
                '}';
    }
}