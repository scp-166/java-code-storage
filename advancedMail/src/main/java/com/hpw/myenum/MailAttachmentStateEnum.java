package com.hpw.myenum;

/**
 * 附件领取状态
 */
public enum  MailAttachmentStateEnum {
    NO_ATTACHMENT(0, "没有附件"),
    NO_RECEIVED(1, "未领取"),
    ALREADY_RECEIVED(2, "已领取"),
    ;
    private Integer attachmentState;
    private String desc;

    MailAttachmentStateEnum(Integer attachmentState, String desc) {
        this.attachmentState = attachmentState;
        this.desc = desc;
    }

    public Integer getAttachmentState() {
        return attachmentState;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return "MailAttachmentStateEnum{" +
                "attachmentState=" + attachmentState +
                ", desc='" + desc + '\'' +
                '}';
    }
}
