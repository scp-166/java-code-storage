package com.hpw.server.slot.constant;

/**
 * @author lyl
 * @date 2020/9/3
 */
public enum PatternTypeEnum {
    /**
     * 图案类型
     */
    UNKNOWN(-1, "未知"),
    NORMAL(0, "普通图案"),
    WILD(1, "WILD"),
    WILD_X2(2, "WILD_X2"),
    JACKPOT(3, "JACKPOT"),
    BONUS(4, "BONUS"),
    ;
    private final Integer type;
    private final String desc;

    PatternTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static PatternTypeEnum getById(int type) {
        for (PatternTypeEnum value : values()) {
            if (type == value.getType()) {
                return value;
            }
        }
        return UNKNOWN;
    }

    @Override
    public String toString() {
        return "PicTypeEnum{" +
                "type=" + type +
                ", desc='" + desc + '\'' +
                '}';
    }
}
