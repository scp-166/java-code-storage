package com.hpw.myenum.convert;

/**
 * 入口图标标签
 */
public enum TagTypeEnum implements BaseCodeTypeEnum {
    NEW(0, "上新"),
    HOT(1, "热门"),
    VIP(2, "vip限定?"),
    ;
    private Integer type;
    private String desc;

    TagTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return "TagStateEnum{" +
                "state=" + type +
                ", desc='" + desc + '\'' +
                '}';
    }

    @Override
    public Integer getCode() {
        return getType();
    }
}
