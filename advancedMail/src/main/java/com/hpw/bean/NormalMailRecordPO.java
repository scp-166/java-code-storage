package com.hpw.bean;

import java.io.Serializable;

/**
 * Table: c_normal_mail_record
 */
public class NormalMailRecordPO implements Serializable {
    /**
     * 邮件id
     *
     * Table:     c_normal_mail_record
     * Column:    id
     * Nullable:  false
     */
    private Long id;

    /**
     * 发送方用户id
     *
     * Table:     c_normal_mail_record
     * Column:    sender_id
     * Nullable:  false
     */
    private Long senderId;

    /**
     * 接收方用户id
     *
     * Table:     c_normal_mail_record
     * Column:    receiver_id
     * Nullable:  false
     */
    private Long receiverId;

    /**
     * 邮件是否已读 0未读 1已读
     *
     * Table:     c_normal_mail_record
     * Column:    read
     * Nullable:  false
     */
    private Boolean read;

    /**
     * 附件状态 0无附件 1有附件未领取 2有附件已领取
     *
     * Table:     c_normal_mail_record
     * Column:    attachment_state
     * Nullable:  false
     */
    private Integer attachmentState;

    /**
     * 附件内容格式 [[游戏id， 道具id， 道具类型，道具数量], ...]；默认值 empty string
     *
     * Table:     c_normal_mail_record
     * Column:    attachment_content_format
     * Nullable:  false
     */
    private String attachmentContentFormat;

    /**
     * 邮件类型 1系统 2玩家
     *
     * Table:     c_normal_mail_record
     * Column:    mail_type
     * Nullable:  false
     */
    private Integer mailType;

    /**
     * 内容类型, 针对 mail_type = 1 ；默认值 empty string
     *
     * Table:     c_normal_mail_record
     * Column:    content_type
     * Nullable:  false
     */
    private Integer contentType;

    /**
     * 对格式化字符串的填充内容, 以 ; 分割；默认值 empty string
     *
     * Table:     c_normal_mail_record
     * Column:    content_args
     * Nullable:  false
     */
    private String contentArgs;

    /**
     * 内容，针对 mail_type = 2 ; 默认值 empty string
     *
     * Table:     c_normal_mail_record
     * Column:    content
     * Nullable:  false
     */
    private String content;

    /**
     * 发送时间
     *
     * Table:     c_normal_mail_record
     * Column:    sending_time
     * Nullable:  false
     */
    private Long sendingTime;

    /**
     * 是否删除
     *
     * Table:     c_normal_mail_record
     * Column:    deleted
     * Nullable:  false
     */
    private Boolean deleted;

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

    public Integer getMailType() {
        return mailType;
    }

    public void setMailType(Integer mailType) {
        this.mailType = mailType;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Long getSendingTime() {
        return sendingTime;
    }

    public void setSendingTime(Long sendingTime) {
        this.sendingTime = sendingTime;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
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
        sb.append(", mailType=").append(mailType);
        sb.append(", contentType=").append(contentType);
        sb.append(", contentArgs=").append(contentArgs);
        sb.append(", content=").append(content);
        sb.append(", sendingTime=").append(sendingTime);
        sb.append(", deleted=").append(deleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}