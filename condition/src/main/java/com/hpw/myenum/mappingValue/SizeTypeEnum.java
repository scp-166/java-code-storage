package com.hpw.myenum.mappingValue;

/**
 * 图标大小
 */
public enum SizeTypeEnum implements BaseCodeTypeEnum{
    SMALL(0, "小尺寸"),
    COMPARABLY_SMALLER(1, "偏小尺寸"),
    MEDIUM(2, "中尺寸"),
    COMPARABLY_BIGGER(3, "偏大尺寸"),
    BIG(4, "大尺寸"),
    ;
    private Integer type;
    private String desc;

    SizeTypeEnum(Integer type, String desc) {
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
        return "IconType{" +
                "type=" + type +
                ", desc='" + desc + '\'' +
                '}';
    }

    @Override
    public Integer getCode() {
        return getType();
    }
}
