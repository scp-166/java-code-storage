package com.hpw.myenum;

public enum MailSenderNameEnum {
    UNKNOWN(-1, "皮卡皮卡"),
    BROAD_CAST(0, "全服1"),
    SYSTEM(1, "系统1"),
    ;
    private Integer senderId;
    private String senderName;

    MailSenderNameEnum(Integer senderId, String senderName) {
        this.senderId = senderId;
        this.senderName = senderName;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    @Override
    public String toString() {
        return "MailSenderNameEnum{" +
                "senderId=" + senderId +
                ", senderName='" + senderName + '\'' +
                '}';
    }



}
