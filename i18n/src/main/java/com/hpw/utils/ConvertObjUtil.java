package com.hpw.utils;

import com.hpw.bean.*;
import com.hpw.service.MailFormatUtil;

import java.util.Objects;

public class ConvertObjUtil {
    private ConvertObjUtil() {
    }

    /**
     * SystemMailRecord 的 po 转 bo 类 <br>
     * <strong>注意:</strong> 由于 languageType 由前端设置，所以此处不进行模板转换，需要调用方自行进行转换 <br>
     * 同理，由于没有模板，所以此处也不进行格式字符串转换
     * 比如 {@code MailConvertUtil.convertI18nMailCompulsive(mail, languageType, mail.getContentType());}
     *
     * @param po db 中原始记录
     * @return SystemMailRecord 业务类
     */
    public static SystemMailRecordBO toSystemMailRecordBO(SystemMailRecordPO po) {
        if (Objects.isNull(po)) {
            return null;
        }

        SystemMailRecordBO bo = new SystemMailRecordBO();
        Mail mail = Mail.Builder.newTemplateInstance(po.getSenderId(), po.getReceiverId(),
                po.getContentType(), po.getRead(), po.getAttachmentState())
                .withSendingTime(po.getSendingTime())
                .withAttachmentContentFormat(po.getAttachmentContentFormat())
                .withContentArgs(po.getContentArgs())
                .withId(po.getId()).build();
        bo.setMail(mail);
        bo.setGameRewardList(GameRewardUtil.jsonString2Rds(mail.getAttachmentContentFormat()));
        return bo;
    }

    /**
     * SystemMailRecord 的 po 转 bo 类 <br>
     * <strong>注意:</strong> 本方法不对 po 中 attachmentContentFormat 字段进行转换<br>
     * 即 SystemMailRecord.gameRewardList 为空
     * <strong>注意:</strong> 由于 languageType 由前端设置，所以此处不进行模板转换，如果需要调用方自行进行转换 <br>
     * 同理，由于没有模板，所以此处也不进行格式字符串转换
     * 比如 {@code MailConvertUtil.convertI18nMailCompulsive(mail, languageType, mail.getContentType());}
     *
     * @param po db 中原始记录
     * @return SystemMailRecord 业务类
     */
    public static SystemMailRecordBO toSystemMailRecordBOWithoutGameRewardList(SystemMailRecordPO po) {
        if (Objects.isNull(po)) {
            return null;
        }

        SystemMailRecordBO bo = new SystemMailRecordBO();
        Mail mail = Mail.Builder.newTemplateInstance(po.getSenderId(), po.getReceiverId(),
                po.getContentType(), po.getRead(), po.getAttachmentState())
                .withSendingTime(po.getSendingTime())
                .withContentArgs(po.getContentArgs())
                .withAttachmentContentFormat(po.getAttachmentContentFormat())
                .withId(po.getId()).build();
        bo.setMail(mail);
        return bo;
    }

    /**
     * SystemMailRecord 的 bo 转 po
     *
     * @param bo SystemMailRecord 业务类
     * @return SystemMailRecord 原始记录类
     */
    public static SystemMailRecordPO toSystemMailRecordPO(SystemMailRecordBO bo) {
        if (Objects.isNull(bo)) {
            return null;
        }
        SystemMailRecordPO po = new SystemMailRecordPO();
        Mail mail = bo.getMail();
        po.setId(mail.getId());
        po.setSenderId(mail.getSenderId());
        po.setReceiverId(mail.getReceiverId());
        po.setAttachmentState(mail.getAttachmentState());
        po.setAttachmentContentFormat(mail.getAttachmentContentFormat());
        po.setContentType(mail.getContentType());
        po.setRead(mail.getRead());
        po.setContentArgs(mail.getContentArgs());
        po.setSendingTime(mail.getSendingTime());
        return po;
    }
}
