package com.hpw.service;

import com.hpw.bean.AdvancedMail;
import com.hpw.bean.BroadcastMailInfoPO;
import com.hpw.dao.BroadcastMailRecordDao;
import com.hpw.dao.MailDao;
import com.hpw.data.NetMessage;
import com.hpw.data.NetResponse;
import com.hpw.myenum.MailAttachmentStateEnum;
import com.hpw.myenum.MailTypeEnum;
import com.hpw.utils.ComparatorUtil;
import org.springframework.util.CollectionUtils;

import java.util.*;

public class MailService {

    public NetResponse init(NetMessage request) {
        NetResponse response = NetMessage.convert2Response(request);
        long userId = request.getUserId();
        byte[] data = request.getData();
        if (Objects.isNull(data)) {
            // todo {@author lyl} add err_code
            return response;
        }

        String params = new String(data);
        String[] paramArr = params.split(",");

        int languageType = Integer.parseInt(paramArr[0]);
        int mailType = Integer.parseInt(paramArr[1]);
        int pageStart = Integer.parseInt(paramArr[2]);
        int expectSinglePageItemSize = Integer.parseInt(paramArr[3]);

        if (mailType == 1) {
            return response;
        } else if (mailType == 2) {
            // todo
            return response;
        } else {
            //todo {@author lyl} add err_code
            return response;
        }
    }

    public NetResponse login(NetMessage request) {
        NetResponse response = NetMessage.convert2Response(request);
        long userId = request.getUserId();

        // fixme 这一步可以在其他位置
        MailDao.getInstance().broadcastMailInfo2LocalCache();

        MailDao.getInstance().targetUserMailRecord2LocalCache(userId);

        // 1. 处理全服邮件
        // 对比服务器内存中的全服邮件是否在玩家列表中
        Map<Long, List<AdvancedMail>> onlineUserMailMap = MailDao.getInstance().getOnlineUserMailWithIdAscMap();
        List<AdvancedMail> currentUserAdvancedMailList = onlineUserMailMap.get(userId);

        List<BroadcastMailInfoPO> broadcastMailList = new ArrayList<>(MailDao.getInstance().getBroadcastMailSet());
        if (!CollectionUtils.isEmpty(broadcastMailList)) {
            AdvancedMail temp;
            AdvancedMail readyInsert;
            for (BroadcastMailInfoPO item : broadcastMailList) {
                temp = new AdvancedMail();
                temp.setId(item.getId());
                int index = Collections.binarySearch(currentUserAdvancedMailList, temp, new ComparatorUtil.MailIdAscComparator<>());
                // 没有该全服邮件，则保存+推送
                if (index < 0) {
                    readyInsert = AdvancedMail.Builder.newBroadcastRecord(item.getContentType(), false,
                            Objects.isNull(item.getAttachmentContentFormat())
                                    ? MailAttachmentStateEnum.NO_ATTACHMENT.getAttachmentState()
                                    : MailAttachmentStateEnum.NO_RECEIVED.getAttachmentState())
                            .withContentArgs(item.getContentArgs())
                            .withSendingTime(item.getSendingTime())
                            .withId(item.getId())
                            .withSenderId((long) MailTypeEnum.BROAD_CAST.getMailType())
                            .withReceiverId(userId)
                            .withAttachmentContentFormat(item.getAttachmentContentFormat())
                            .withMailType(MailTypeEnum.BROAD_CAST.getMailType())
                            .withDeleted(false)
                            // .withContent()
                            // .withSubject()
                            // .withSenderName()
                            .build();
                    if (BroadcastMailRecordDao.getInstance().insertRecord(readyInsert)) {
                        currentUserAdvancedMailList.add(readyInsert);
                        // todo {@author lyl} 推送
                    }
                }
            }
        }
        return response;
    }
}
