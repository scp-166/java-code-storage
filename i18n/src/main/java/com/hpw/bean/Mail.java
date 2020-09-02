package com.hpw.bean;

import com.hpw.service.MailFormatUtil;

public class Mail {
    /**
     * 邮件id
     */
    private Long id;

    /**
     * 发送方用户id
     */
    private Long senderId;

    /**
     * 发送方名称
     */
    private String senderName;

    /**
     * 接收方用户id
     */
    private Long receiverId;

    /**
     * 标题
     */
    private String subject;

    /**
     * 内容
     */
    private String content;

    /**
     * 邮件是否已读 0未读 1已读
     */
    private Boolean read;

    /**
     * 附件状态 0无附件 1有附件未领取 2有附件已领取
     */
    private Integer attachmentState;

    /**
     * 附件内容格式 [[游戏id， 道具id， 道具类型，道具数量], ...]
     */
    private String attachmentContentFormat;

    /**
     * 邮件类型
     */
    private Integer contentType;

    /**
     * 邮件内容格式字符串的参数，以分号分割
     */
    private String contentArgs;

    /**
     * 发送时间
     */
    private Long sendingTime;


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

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
        this.attachmentContentFormat = attachmentContentFormat;
    }

    public Long getSendingTime() {
        return sendingTime;
    }

    public void setSendingTime(Long sendingTime) {
        this.sendingTime = sendingTime;
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
        this.contentArgs = contentArgs;
    }

    @Override
    public String toString() {
        return "Mail{" +
                "id=" + id +
                ", senderId=" + senderId +
                ", senderName='" + senderName + '\'' +
                ", receiverId=" + receiverId +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", read=" + read +
                ", attachmentState=" + attachmentState +
                ", attachmentContentFormat='" + attachmentContentFormat + '\'' +
                ", typeId=" + contentType +
                ", sendingTime=" + sendingTime +
                '}';
    }

    public void contentFormat(Object[] args) {
        this.content = MailFormatUtil.format(this.content, args);
    }

    public static final class Builder {
        private Long id;
        private Long senderId;
        private String senderName;
        private Long receiverId;
        private String subject;
        private String content;
        private Boolean read;
        private Integer attachmentState;
        private String attachmentContentFormat;
        private Integer contentType;
        private String contentArgs;
        private Long sendingTime;

        private Builder() {
        }

        public Builder(Long senderId, Long receiverId, Integer contentType,
                       Boolean read, Integer attachmentState) {
            this.senderId = senderId;
            this.receiverId = receiverId;
            this.contentType = contentType;
            this.read = read;
            this.attachmentState = attachmentState;
        }

        /**
         * 通用 mail
         */
        public static Builder newInstance() {
            return new Builder();
        }

        /**
         * 创建模板 Mail
         */
        public static Builder newTemplateInstance(Long senderId, Long receiverId, Integer typeId,
                                                  Boolean read, Integer attachmentState) {
            return new Builder(senderId, receiverId, typeId,
                    read, attachmentState);
        }

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withSenderId(Long senderId) {
            this.senderId = senderId;
            return this;
        }

        public Builder withReceiverId(Long receiverId) {
            this.receiverId = receiverId;
            return this;
        }

        public Builder withTypeId(Integer typeId) {
            this.contentType = typeId;
            return this;
        }

        public Builder withContentArgs(String contentArgs) {
            this.contentArgs = contentArgs;
            return this;
        }

        public Builder withRead(Boolean read) {
            this.read = read;
            return this;
        }


        public Builder withSenderName(String senderName) {
            this.senderName = senderName;
            return this;
        }

        public Builder withSubject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder withContent(String content) {
            this.content = content;
            return this;
        }

        public Builder withAttachmentState(Integer attachmentState) {
            this.attachmentState = attachmentState;
            return this;
        }

        public Builder withAttachmentContentFormat(String attachmentContentFormat) {
            this.attachmentContentFormat = attachmentContentFormat;
            return this;
        }

        public Builder withSendingTime(Long sendingTime) {
            this.sendingTime = sendingTime;
            return this;
        }

        public Mail build() {
            Mail mail = new Mail();
            mail.setId(this.id);
            mail.setSenderId(this.senderId);
            mail.setReceiverId(this.receiverId);
            mail.setSenderName(this.senderName);
            mail.setContent(this.content);
            mail.setSubject(this.subject);
            mail.setRead(this.read);
            mail.setAttachmentState(this.attachmentState);
            mail.setAttachmentContentFormat(this.attachmentContentFormat);
            mail.setContentType(this.contentType);
            mail.setContentArgs(this.contentArgs);
            mail.setSendingTime(this.sendingTime);
            return mail;
        }
    }


}