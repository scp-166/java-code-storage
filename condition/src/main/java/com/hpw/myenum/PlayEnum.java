package com.hpw.myenum;

/**
 * 玩法枚举
 */
public enum PlayEnum {
    JACK_POT(0, "jackPot玩法")
    ;
    private Integer type;
    private String desc;

    PlayEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static PlayEnum getByType(Integer type) {
        for (PlayEnum value : values()) {
            if (value.getType().equals(type)) {
                return value;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "PlayEnum{" +
                "type=" + type +
                ", desc='" + desc + '\'' +
                '}';
    }
}
