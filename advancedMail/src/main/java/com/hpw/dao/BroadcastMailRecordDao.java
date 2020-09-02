package com.hpw.dao;

import com.hpw.ComMapperFactory;
import com.hpw.bean.*;

import com.hpw.dao.mapper.BroadcastMailRecordPOMapper;
import org.springframework.util.CollectionUtils;

import java.util.*;


public class BroadcastMailRecordDao {
    private static class Holder {
        public static BroadcastMailRecordDao instance = new BroadcastMailRecordDao();
    }

    public static BroadcastMailRecordDao getInstance() {
        return Holder.instance;
    }

    private BroadcastMailRecordPOMapper recordMapper = ComMapperFactory.getInstance().getMapper(BroadcastMailRecordPOMapper.class);

    public boolean insertRecord(AdvancedMail advancedMail) {
        BroadcastMailRecordPO po = new BroadcastMailRecordPO();
        po.setReceiverId(advancedMail.getReceiverId());
        po.setBroadcastMailId(advancedMail.getId());
        po.setRead(advancedMail.getRead());
        po.setAttachmentState(advancedMail.getAttachmentState());
        po.setDeleted(advancedMail.getDeleted());
        int ret = recordMapper.addRecord(po);
        if (checkRet(ret)) {
            // delete cache
        }
        return checkRet(ret);
    }

    public List<BroadcastMailRecordPO> getRecordByReceiverId(long receiverId) {
        List<BroadcastMailRecordPO> recordList = recordMapper.getRecordByReceiverId(receiverId);
        if (!CollectionUtils.isEmpty(recordList)) {
            // todo add cache
        }
        return recordList;
    }

    public boolean updateRecordList(List<AdvancedMail> broadcastAdvancedMailRecordList) {
        if (CollectionUtils.isEmpty(broadcastAdvancedMailRecordList)) {
            return true;
        }
        List<BroadcastMailRecordPO> poList = new ArrayList<>(broadcastAdvancedMailRecordList.size());
        BroadcastMailRecordPO po;
        for (AdvancedMail advancedMail : broadcastAdvancedMailRecordList) {
            po = new BroadcastMailRecordPO();
            po.setReceiverId(advancedMail.getReceiverId());
            po.setBroadcastMailId(advancedMail.getId());
            po.setAttachmentState(advancedMail.getAttachmentState());
            po.setRead(advancedMail.getRead());
            po.setDeleted(advancedMail.getDeleted());
            poList.add(po);
        }
        int ret = recordMapper.updateRecordList(poList);
        if (checkRet(ret)) {
            // todo delete cache
        }
        return checkRet(ret);
    }

    /**
     * 全服邮件由于其特殊性 (上线自动拉取) 不要物理删除
     */
    public boolean logicDeleteRecordList(List<AdvancedMail> deleteBroadcastAdvancedMailRecordList) {
        if (CollectionUtils.isEmpty(deleteBroadcastAdvancedMailRecordList)) {
            return true;
        }
        List<BroadcastMailRecordPO> deleteMailList = new ArrayList<>(deleteBroadcastAdvancedMailRecordList.size());
        BroadcastMailRecordPO temp;
        for (AdvancedMail advancedMail : deleteBroadcastAdvancedMailRecordList) {
            temp = new BroadcastMailRecordPO();
            temp.setReceiverId(advancedMail.getReceiverId());
            temp.setBroadcastMailId(advancedMail.getId());
            deleteMailList.add(temp);
        }

        int ret = recordMapper.logicDeleteByIdList(deleteMailList);
        if (checkRet(ret)){
            // todo delete cache
        }
        return checkRet(ret);
    }

    public boolean checkRet(int affectRow) {
        return affectRow >= 0;
    }
}