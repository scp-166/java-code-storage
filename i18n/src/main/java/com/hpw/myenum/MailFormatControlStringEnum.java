package com.hpw.myenum;

/**
 * 格式化字符串
 */
public enum MailFormatControlStringEnum {
    UNKNOWN("#<unknown>", "未知"),
    NAME("#<name>", "名称"),
    MONEY("#<money>", "金额"),
    PLACE("#<place>", "位置"),
    DAY("#<day>", "天数"),
    DATE("#<date>", "时间"),
    ;
    private String formatControlString;
    private String desc;

    MailFormatControlStringEnum(String formatControlString, String desc) {
        this.formatControlString = formatControlString;
        this.desc = desc;
    }

    public String getFormatControlString() {
        return formatControlString;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return "MailFormatControlStringEnum{" +
                "formatControlString='" + formatControlString + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }

    public static MailFormatControlStringEnum getByFormatControlString(String formatControlString) {
        for (MailFormatControlStringEnum item : values()) {
            if (item.getFormatControlString().equals(formatControlString)) {
                return item;
            }
        }
        return UNKNOWN;
    }

}
