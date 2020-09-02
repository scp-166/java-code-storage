package com.hpw.myenum;

public enum MailAttachmentStateEnum {
    NO_ATTACHMENT(0, "无附件"),
    NO_OBTAINED(1, "有附件，未获取"),
    OBTAINED(2, "有附加, 已获取"),
    ;
    private Integer state;
    private String desc;

    MailAttachmentStateEnum(Integer state, String desc) {
        this.state = state;
        this.desc = desc;
    }

    public Integer getState() {
        return state;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return "MailAttachmentStateEnum{" +
                "state=" + state +
                ", desc='" + desc + '\'' +
                '}';
    }
}
