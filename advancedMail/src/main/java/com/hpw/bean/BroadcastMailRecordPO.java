package com.hpw.bean;

import java.io.Serializable;

/**
 * Table: c_broadcast_mail_record
 */
public class BroadcastMailRecordPO implements Serializable {
    /**
     * 接收方id
     *
     * Table:     c_broadcast_mail_record
     * Column:    receiver_id
     * Nullable:  false
     */
    private Long receiverId;

    /**
     * 全局邮件id，同 c_global_mail_record
     *
     * Table:     c_broadcast_mail_record
     * Column:    broadcast_mail_id
     * Nullable:  false
     */
    private Long broadcastMailId;

    /**
     * 是否已读
     *
     * Table:     c_broadcast_mail_record
     * Column:    read
     * Nullable:  false
     */
    private Boolean read;

    /**
     * 附件状态
     *
     * Table:     c_broadcast_mail_record
     * Column:    attachment_state
     * Nullable:  false
     */
    private Integer attachmentState;

    /**
     * 是否删除
     *
     * Table:     c_broadcast_mail_record
     * Column:    deleted
     * Nullable:  false
     */
    private Boolean deleted;

    private static final long serialVersionUID = 1L;

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public Long getBroadcastMailId() {
        return broadcastMailId;
    }

    public void setBroadcastMailId(Long broadcastMailId) {
        this.broadcastMailId = broadcastMailId;
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
        sb.append(", receiverId=").append(receiverId);
        sb.append(", broadcastMailId=").append(broadcastMailId);
        sb.append(", read=").append(read);
        sb.append(", attachmentState=").append(attachmentState);
        sb.append(", deleted=").append(deleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}