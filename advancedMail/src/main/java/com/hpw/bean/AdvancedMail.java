package com.hpw.bean;

public class AdvancedMail {
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
     * 邮件类型: 全服0、系统1、好友2 (db判断和前端判断)
     */
    private Integer mailType;

    /**
     * 邮件内容类型
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

    /**
     * 是否删除, 用于全服邮件的删除，为 1 时前端不显示，通过时间自动删除
     */
    private Boolean deleted;


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
        this.contentArgs = contentArgs;
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
                ", mailType=" + mailType +
                ", contentType=" + contentType +
                ", contentArgs='" + contentArgs + '\'' +
                ", sendingTime=" + sendingTime +
                ", deleted=" + deleted +
                '}';
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
        private Integer mailType;
        private Integer contentType;
        private String contentArgs;
        private Long sendingTime;
        private Boolean deleted;

        private Builder() {
        }

        public Builder(Long id, Long senderId, String senderName, Long receiverId,
                       String subject, String content, Boolean read,
                       Integer attachmentState, String attachmentContentFormat,
                       Integer mailType, Integer contentType, String contentArgs,
                       Long sendingTime, Boolean deleted) {
            this.id = id;
            this.senderId = senderId;
            this.senderName = senderName;
            this.receiverId = receiverId;
            this.subject = subject;
            this.content = content;
            this.read = read;
            this.attachmentState = attachmentState;
            this.attachmentContentFormat = attachmentContentFormat;
            this.mailType = mailType;
            this.contentType = contentType;
            this.contentArgs = contentArgs;
            this.sendingTime = sendingTime;
            this.deleted = deleted;
        }

        /**
         * 通用 mail
         * 剩余需要新增的属性有
         * <pre>
         *     withSenderName()
         *     withSubject()
         *     withContent()
         *     withContentType()
         *     withContentArgs()
         * </pre>
         */
        public static Builder newInstance(Long mailId, Long senderId, Long receiverId,
                                          Integer mailType, Integer attachmentState, String attachmentContentFormat,
                                          Boolean read, Boolean deleted, Long sendingTime) {
            return new Builder(mailId, senderId, null, receiverId, null, null, read,
                    attachmentState, attachmentContentFormat,
                    mailType, null, null, sendingTime, deleted);
        }

        /**
         * 创建广播 Mail
         */
        public static Builder newBroadcastRecord(Integer contentType, Boolean read, Integer attachmentState) {
            return new Builder(null, null, null, null,
                    null, null, read,
                    attachmentState, null,
                    null, contentType, null, null, null);
        }

        public static Builder newBroadcastInfo(Long mailId, Integer contentType, String contentArgs,
                                               String attachmentContentFormat, Long sendingTime) {
            return new Builder(mailId, null, null, null,
                    null, null, null,
                    null, attachmentContentFormat,
                    null, contentType, contentArgs, sendingTime, null);
        }

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withMailType(Integer mailType) {
            this.mailType = mailType;
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

        public Builder withContentType(Integer contentType) {
            this.contentType = contentType;
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

        public Builder withDeleted(Boolean deleted) {
            this.deleted = deleted;
            return this;
        }

        public AdvancedMail build() {
            AdvancedMail advancedMail = new AdvancedMail();
            advancedMail.setId(this.id);
            advancedMail.setSenderId(this.senderId);
            advancedMail.setReceiverId(this.receiverId);
            advancedMail.setSenderName(this.senderName);
            advancedMail.setContent(this.content);
            advancedMail.setSubject(this.subject);
            advancedMail.setRead(this.read);
            advancedMail.setAttachmentState(this.attachmentState);
            advancedMail.setAttachmentContentFormat(this.attachmentContentFormat);
            advancedMail.setMailType(this.mailType);
            advancedMail.setContentType(this.contentType);
            advancedMail.setContentArgs(this.contentArgs);
            advancedMail.setSendingTime(this.sendingTime);
            advancedMail.setDeleted(this.deleted);
            return advancedMail;
        }
    }


}