package com.hpw.bean;

import java.io.Serializable;

/**
 * Table: c_system_mail_record
 */
public class SystemMailRecordPO implements Serializable {
    /**
     * 邮件id
     *
     * Table:     c_system_mail_record
     * Column:    id
     * Nullable:  false
     */
    private Long id;

    /**
     * 发送方用户id
     *
     * Table:     c_system_mail_record
     * Column:    sender_id
     * Nullable:  false
     */
    private Long senderId;

    /**
     * 接收方用户id
     *
     * Table:     c_system_mail_record
     * Column:    receiver_id
     * Nullable:  false
     */
    private Long receiverId;

    /**
     * 邮件是否已读 0未读 1已读
     *
     * Table:     c_system_mail_record
     * Column:    read
     * Nullable:  false
     */
    private Boolean read;

    /**
     * 附件状态 0无附件 1有附件未领取 2有附件已领取
     *
     * Table:     c_system_mail_record
     * Column:    attachment_state
     * Nullable:  false
     */
    private Integer attachmentState;

    /**
     * 附件内容格式 [[游戏id， 道具id， 道具类型，道具数量], ...]
     *
     * Table:     c_system_mail_record
     * Column:    attachment_content_format
     * Nullable:  true
     */
    private String attachmentContentFormat;

    /**
     * 内容类型
     *
     * Table:     c_system_mail_record
     * Column:    content_type
     * Nullable:  false
     */
    private Integer contentType;

    /**
     * 对格式化字符串的填充内容, 以 ; 分割
     *
     * Table:     c_system_mail_record
     * Column:    content_args
     * Nullable:  true
     */
    private String contentArgs;

    /**
     * 发送时间
     *
     * Table:     c_system_mail_record
     * Column:    sending_time
     * Nullable:  false
     */
    private Long sendingTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public Integer getAttachmentState() {
        return attachmentState;
    }

    public void setAttachmentState(Integer attachmentState) {
        this.attachmentState = attachmentState;
    }

    public String getAttachmentContentFormat() {
        return attachmentContentFormat;
    }

    public void setAttachmentContentFormat(String attachmentContentFormat) {
        this.attachmentContentFormat = attachmentContentFormat == null ? null : attachmentContentFormat.trim();
    }

    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    public String getContentArgs() {
        return contentArgs;
    }

    public void setContentArgs(String contentArgs) {
        this.contentArgs = contentArgs == null ? null : contentArgs.trim();
    }

    public Long getSendingTime() {
        return sendingTime;
    }

    public void setSendingTime(Long sendingTime) {
        this.sendingTime = sendingTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", senderId=").append(senderId);
        sb.append(", receiverId=").append(receiverId);
        sb.append(", read=").append(read);
        sb.append(", attachmentState=").append(attachmentState);
        sb.append(", attachmentContentFormat=").append(attachmentContentFormat);
        sb.append(", contentType=").append(contentType);
        sb.append(", contentArgs=").append(contentArgs);
        sb.append(", sendingTime=").append(sendingTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}