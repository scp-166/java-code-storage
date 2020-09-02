package com.hpw.service;

import com.hpw.bean.*;
import com.hpw.dao.SystemMailRecordDao;
import com.hpw.data.NetMessage;
import com.hpw.data.NetResponse;
import com.hpw.net.msg.NetMail;
import com.hpw.net.msg.NetMailInit;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

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
            DbPage<SystemMailRecordBO> recordPage =
                    SystemMailRecordDao.getInstance().getRecordPageWithRewardList(
                            userId, false, pageStart, expectSinglePageItemSize, languageType);

            if (Objects.isNull(recordPage)) {
                // todo {@author lyl} no page error
                return response;
            }
            List<SystemMailRecordBO> currentPageRecordList = recordPage.getCurrentPageRecordList();

            NetMailInit.Builder builder = NetMailInit.newBuilder();
            builder.setCurrentPageNum(recordPage.getCurrentPageNum());
            builder.setCurrentPageItemCount(recordPage.getCurrentPageItemCount());
            builder.setTotalPageSize(recordPage.getTotalPageSize());
            builder.setTotoalItemCount(recordPage.getTotalPageItemCount());

            if (!CollectionUtils.isEmpty(currentPageRecordList)) {
                NetMail.Builder mailBuilder;
                for (SystemMailRecordBO recordBO : currentPageRecordList) {
                    Mail mail = recordBO.getMail();
                    mailBuilder = NetMail.newBuilder();
                    mailBuilder.setId(mail.getId());
                    mailBuilder.setSenderId(mail.getSenderId());
                    mailBuilder.setSenderName(mail.getSenderName());
                    mailBuilder.setReceiverId(mail.getReceiverId());
                    mailBuilder.setRead(mail.getRead());
                    if (Objects.nonNull(mail.getAttachmentContentFormat())) {
                        mailBuilder.setAttachmentContentFormat(mail.getAttachmentContentFormat());
                    }
                    mailBuilder.setAttachmentState(mail.getAttachmentState());
                    mailBuilder.setSubject(mail.getSubject());
                    mailBuilder.setSendingTime(mail.getSendingTime());
                    // todo {@author lyl} 系统类型是否需要补充进 mail 中
                    mailBuilder.setMailType(1);

                    builder.addMailList(mailBuilder);
                }
            }
            response.setData(builder.build().toByteArray());
            return response;
        } else if (mailType == 2) {
            // todo
            return response;
        } else {
            //todo {@author lyl} add err_code
            return response;
        }

    }
}
