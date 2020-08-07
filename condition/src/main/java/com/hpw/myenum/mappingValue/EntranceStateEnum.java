package com.hpw.myenum.mappingValue;

/**
 * 入口状态
 */
public enum EntranceStateEnum implements BaseCodeTypeEnum {
    OFF_THE_SHELF(0, "下架处理"),
    COMING_SOON(1, "敬请期待"),
    ACTIVE(2, "激活"),
    ;
    private Integer state;
    private String desc;

    EntranceStateEnum(Integer state, String desc) {
        this.state = state;
        this.desc = desc;
    }

    public Integer getState() {
        return state;
    }

    public String getDesc() {
        return desc;
    }


    @Override
    public Integer getCode() {
        return getState();
    }

    public static EntranceStateEnum getByState(int state) {
        for (EntranceStateEnum value : values()) {
            if (value.getState().equals(state)) {
                return value;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "EntranceState{" +
                "state=" + state +
                ", desc='" + desc + '\'' +
                '}';
    }

}
