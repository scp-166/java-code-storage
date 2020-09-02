package com.hpw.dao.mapper;

import com.hpw.ComMapperFactory;
import com.hpw.bean.AdvancedMail;
import com.hpw.bean.NormalMailRecordPO;
import com.hpw.myenum.MailTypeEnum;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;


public class NormalMailRecordDao {
    private static class Holder {
        public static NormalMailRecordDao instance = new NormalMailRecordDao();
    }

    public static NormalMailRecordDao getInstance() {
        return Holder.instance;
    }

    private NormalMailRecordPOMapper mapper = ComMapperFactory.getInstance().getMapper(NormalMailRecordPOMapper.class);

    public boolean insertRecord(AdvancedMail advancedMail) {
        if (Objects.isNull(advancedMail.getMailType())) {
            System.out.println("邮件类型不能为空");
            return false;
        }
        NormalMailRecordPO po = new NormalMailRecordPO();
        po.setId(advancedMail.getId());
        po.setSenderId(advancedMail.getSenderId());
        po.setReceiverId(advancedMail.getReceiverId());
        po.setRead(advancedMail.getRead());
        po.setAttachmentState(advancedMail.getAttachmentState());
        po.setAttachmentContentFormat(advancedMail.getAttachmentContentFormat());
        po.setDeleted(advancedMail.getDeleted());
        po.setSendingTime(advancedMail.getSendingTime());
        po.setMailType(advancedMail.getMailType());

        if (MailTypeEnum.SYSTEM.getMailType().equals(advancedMail.getMailType())) {
            po.setContentType(advancedMail.getContentType());
            po.setContentArgs(advancedMail.getContentArgs());
        } else if (MailTypeEnum.FRIEND.getMailType().equals(advancedMail.getMailType())) {
            po.setContent(advancedMail.getContent());
        } else {
            System.out.println("邮件类型不匹配");
            return false;
        }
        int ret = mapper.addRecord(po);

        if (checkRet(ret)) {
            // delete cache
        }
        return checkRet(ret);
    }

    public List<NormalMailRecordPO> getRecordByUserId(long userId, boolean isSender) {
        List<NormalMailRecordPO> recordList = mapper.getRecordListByUserId(userId, isSender);
        if (!CollectionUtils.isEmpty(recordList)) {
            //     todo add cache
        }
        return recordList;
    }

    public boolean updateRecordList(List<AdvancedMail> broadcastAdvancedMailRecordList) {
        // if (CollectionUtils.isEmpty(broadcastMailRecordList)) {
        //     return true;
        // }
        // List<BroadcastMailRecordPO> poList = new ArrayList<>(broadcastMailRecordList.size());
        // BroadcastMailRecordPO po;
        // for (Mail mail : broadcastMailRecordList) {
        //     po = new BroadcastMailRecordPO();
        //     po.setReceiverId(mail.getReceiverId());
        //     po.setBroadcastMailId(mail.getId());
        //     po.setAttachmentState(mail.getAttachmentState());
        //     po.setRead(mail.getRead());
        //     po.setDeleted(mail.getDeleted());
        //     poList.add(po);
        // }
        // int ret = recordMapper.updateRecordList(poList);
        // if (checkRet(ret)) {
        //     // todo delete cache
        // }
        // return checkRet(ret);
        return false;
    }

    /**
     * 全服邮件由于其特殊性 (上线自动拉取) 不要物理删除
     */
    public boolean logicDeleteRecordList(List<AdvancedMail> deleteBroadcastAdvancedMailRecordList) {
        // if (CollectionUtils.isEmpty(deleteBroadcastMailRecordList)) {
        //     return true;
        // }
        // List<BroadcastMailRecordPO> deleteMailList = new ArrayList<>(deleteBroadcastMailRecordList.size());
        // BroadcastMailRecordPO temp;
        // for (Mail mail : deleteBroadcastMailRecordList) {
        //     temp = new BroadcastMailRecordPO();
        //     temp.setReceiverId(mail.getReceiverId());
        //     temp.setBroadcastMailId(mail.getId());
        //     deleteMailList.add(temp);
        // }
        //
        // int ret = recordMapper.logicDeleteByIdList(deleteMailList);
        // if (checkRet(ret)){
        //     // todo delete cache
        // }
        // return checkRet(ret);
        return false;
    }

    public boolean checkRet(int affectRow) {
        return affectRow >= 0;
    }
}