package com.hpw.utils;

import com.hpw.bean.AdvancedMail;
import com.hpw.bean.BroadcastMailInfoPO;
import com.hpw.bean.BroadcastMailRecordBO;
import com.hpw.bean.BroadcastMailRecordPO;
import com.hpw.myenum.MailTypeEnum;

import java.util.Objects;

public class ConvertObjectUtil {
    private ConvertObjectUtil() {
    }

    public static BroadcastMailRecordBO toBroadcastMailRecordBO(BroadcastMailRecordPO recordPO, BroadcastMailInfoPO infoPO) {
        if (Objects.isNull(recordPO) || Objects.isNull(infoPO)) {
            System.out.println("recordPO or infoPO is null");
            return null;
        }
        AdvancedMail advancedMail = AdvancedMail.Builder.newBroadcastRecord(infoPO.getContentType(), recordPO.getRead(), recordPO.getAttachmentState())
                .withContentArgs(infoPO.getContentArgs())
                .withSendingTime(infoPO.getSendingTime())
                .withId(infoPO.getId())
                .withSenderId((long)MailTypeEnum.BROAD_CAST.getMailType())
                .withReceiverId(recordPO.getReceiverId())
                .withAttachmentContentFormat(infoPO.getAttachmentContentFormat())
                .withMailType(MailTypeEnum.BROAD_CAST.getMailType())
                // .withContent()
                // .withSubject()
                // .withSenderName()
                .build();
        BroadcastMailRecordBO bo = new BroadcastMailRecordBO();
        bo.setAdvancedMail(advancedMail);
        bo.setGameRewardList(GameRewardUtil.jsonString2Rds(advancedMail.getAttachmentContentFormat()));
        return bo;
    }

    public static BroadcastMailInfoPO toBroadcastMailInfoPO(BroadcastMailRecordBO bo) {
        if (Objects.isNull(bo) || Objects.isNull(bo.getAdvancedMail())) {
            System.out.println("bo or bo.getMail() is null" + bo);
            return null;
        }
        BroadcastMailInfoPO po = new BroadcastMailInfoPO();
        po.setId(bo.getAdvancedMail().getId());
        po.setContentType(bo.getAdvancedMail().getContentType());
        po.setContentArgs(bo.getAdvancedMail().getContentArgs());
        po.setAttachmentContentFormat(bo.getAdvancedMail().getAttachmentContentFormat());
        po.setSendingTime(bo.getAdvancedMail().getSendingTime());
        return po;
    }
}
