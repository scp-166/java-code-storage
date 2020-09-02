package com.hpw.bean;

import java.io.Serializable;
import java.util.Objects;

/**
 * Table: c_broadcast_mail_info
 */
public class BroadcastMailInfoPO implements Serializable {
    /**
     * 邮件id
     *
     * Table:     c_broadcast_mail_info
     * Column:    id
     * Nullable:  false
     */
    private Long id;

    /**
     * 附件内容格式 [[游戏id， 道具id， 道具类型，道具数量], ...]；默认值 empty string
     *
     * Table:     c_broadcast_mail_info
     * Column:    attachment_content_format
     * Nullable:  false
     */
    private String attachmentContentFormat;

    /**
     * 内容类型
     *
     * Table:     c_broadcast_mail_info
     * Column:    content_type
     * Nullable:  false
     */
    private Integer contentType;

    /**
     * 对格式化字符串的填充内容, 以 ; 分割 ； 默认值 empty string
     *
     * Table:     c_broadcast_mail_info
     * Column:    content_args
     * Nullable:  false
     */
    private String contentArgs;

    /**
     * 发送时间
     *
     * Table:     c_broadcast_mail_info
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
        sb.append(", attachmentContentFormat=").append(attachmentContentFormat);
        sb.append(", contentType=").append(contentType);
        sb.append(", contentArgs=").append(contentArgs);
        sb.append(", sendingTime=").append(sendingTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BroadcastMailInfoPO po = (BroadcastMailInfoPO) o;
        return Objects.equals(id, po.id) &&
                Objects.equals(attachmentContentFormat, po.attachmentContentFormat) &&
                Objects.equals(contentType, po.contentType) &&
                Objects.equals(contentArgs, po.contentArgs) &&
                Objects.equals(sendingTime, po.sendingTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, attachmentContentFormat, contentType, contentArgs, sendingTime);
    }
}