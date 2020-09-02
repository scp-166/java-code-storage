package com.hpw.myenum;

import java.util.Objects;

/**
 * 邮件类型 enum
 */
public enum MailLanguageEnum {
    DEFAULT(0, "默认英语"),
    ENGLISH(1, "英语"),
    THAI(2, "泰语"),
    ;
    private Integer languageId;
    private String desc;

    private static volatile MailLanguageEnum[] cache;


    MailLanguageEnum(Integer languageId, String desc) {
        this.languageId = languageId;
        this.desc = desc;
    }

    public static MailLanguageEnum getMailTypeEnumByLanguageId(Integer languageId) {
        if (Objects.isNull(cache)) {
            cache = values();
        }
        for (MailLanguageEnum mailTypeEnum : cache) {
            if (mailTypeEnum.getLanguageId().equals(languageId)) {
                return mailTypeEnum;
            }
        }
        return DEFAULT;
    }

    public Integer getLanguageId() {
        return languageId;
    }

    public String getDesc() {
        return desc;
    }

    public MailLanguageEnum[] getCache() {
        if (Objects.isNull(cache)) {
            cache = values();
        }
        return cache;
    }

    @Override
    public String toString() {
        return "MailLanguageEnum{" +
                "languageId=" + languageId +
                ", desc='" + desc + '\'' +
                '}';
    }

    public static void main(String[] args) {
        System.out.println(THAI.getMailTypeEnumByLanguageId(2));
    }
}
