package com.hpw.myenum;

import java.util.Objects;

/**
 * 邮件类型 enum
 */
public enum MailTypeEnum {
    WELCOME(1, "欢迎"),
    ONLINE(2, "在线奖励"),
    ;
    private Integer contentType;
    private String desc;

    private static volatile MailTypeEnum[] cache;


    MailTypeEnum(Integer contentType, String desc) {
        this.contentType = contentType;
        this.desc = desc;
    }

    public MailTypeEnum getMailTypeEnumByTypeId(Integer typeId) {
        if (Objects.isNull(cache)) {
            cache = values();
        }
        for (MailTypeEnum mailTypeEnum : cache) {
            if (mailTypeEnum.getContentType().equals(typeId)) {
                return mailTypeEnum;
            }
        }
        return null;
    }

    public Integer getContentType() {
        return contentType;
    }

    public String getDesc() {
        return desc;
    }

    public MailTypeEnum[] getCache() {
        if (Objects.isNull(cache)) {
            cache = values();
        }
        return cache;
    }

    @Override
    public String toString() {
        return "MailTypeEnum{" +
                "typeId=" + contentType +
                ", desc='" + desc + '\'' +
                '}';
    }

    public static void main(String[] args) {
        System.out.println(WELCOME.getMailTypeEnumByTypeId(2));
    }
}
