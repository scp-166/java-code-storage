package com.hpw.myenum;

import java.util.Objects;

/**
 * 邮件类型 enum
 */
public enum MailSystemNameEnum {
    DEFAULT(0, "system"),
    ENGLISH(1, "system"),
    THAI(2, "ระบบ"),
    ;
    private Integer languageType;
    private String systemName;

    private static volatile MailSystemNameEnum[] cache;


    MailSystemNameEnum(Integer languageType, String systemName) {
        this.languageType = languageType;
        this.systemName = systemName;
    }

    public static MailSystemNameEnum getMailTypeEnumByLanguageId(Integer languageId) {
        if (Objects.isNull(cache)) {
            cache = values();
        }
        for (MailSystemNameEnum mailTypeEnum : cache) {
            if (mailTypeEnum.getLanguageType().equals(languageId)) {
                return mailTypeEnum;
            }
        }
        return DEFAULT;
    }

    public Integer getLanguageType() {
        return languageType;
    }

    public String getSystemName() {
        return systemName;
    }

    public MailSystemNameEnum[] getCache() {
        if (Objects.isNull(cache)) {
            cache = values();
        }
        return cache;
    }

    @Override
    public String toString() {
        return "MailLanguageEnum{" +
                "languageId=" + languageType +
                ", desc='" + systemName + '\'' +
                '}';
    }

    public static void main(String[] args) {
        System.out.println(THAI.getMailTypeEnumByLanguageId(2));
    }
}
