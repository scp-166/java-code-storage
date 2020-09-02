package com.hpw.myenum;

import java.util.Objects;

public enum MailTypeEnum {
    UNKNOWN(-1, "未知类型"),
    BROAD_CAST(0, "广播邮件"),
    SYSTEM(1, "系统邮件"),
    FRIEND(2, "好友邮件"),
    ;
    private Integer mailType;
    private String desc;

    MailTypeEnum(Integer mailType, String desc) {
        this.mailType = mailType;
        this.desc = desc;
    }

    public Integer getMailType() {
        return mailType;
    }

    public static MailTypeEnum getByMailType(Integer mailType) {
        if (Objects.isNull(mailType)) {
            return UNKNOWN;
        }
        for (MailTypeEnum value : MailTypeEnum.values()) {
            if (value.getMailType().equals(mailType)) {
                return value;
            }
        }
        return UNKNOWN;
    }


    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return "MailTypeEnum{" +
                "mailType=" + mailType +
                ", desc='" + desc + '\'' +
                '}';
    }
}
