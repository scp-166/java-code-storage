package com.hpw.dao;

import com.hpw.ComMapperFactory;
import com.hpw.bean.AdvancedMail;
import com.hpw.bean.BroadcastMailInfoPO;
import com.hpw.dao.mapper.BroadcastMailInfoPOMapper;
import org.springframework.util.CollectionUtils;

import java.util.List;


public class BroadcastMailInfoDao {
    private static class Holder {
        public static BroadcastMailInfoDao instance = new BroadcastMailInfoDao();
    }

    public static BroadcastMailInfoDao getInstance() {
        return Holder.instance;
    }

    private BroadcastMailInfoPOMapper infoMapper = ComMapperFactory.getInstance().getMapper(BroadcastMailInfoPOMapper.class);


    /**
     * 获得所有广播邮件信息
     *
     * @return SystemMailRecord 业务类
     */
    public List<BroadcastMailInfoPO> getAllInfo() {
        List<BroadcastMailInfoPO> infoPOList = infoMapper.selectAllInfo();
        if (!CollectionUtils.isEmpty(infoPOList)) {
            // todo add cache
        }
        return infoPOList;
    }


    /**
     * 获得所有广播邮件信息, 按照 sendingTime 排序
     *
     * @return SystemMailRecord 业务类
     */
    public List<BroadcastMailInfoPO> getAllInfoOrderBySendingTime() {
        List<BroadcastMailInfoPO> infoPOList = infoMapper.selectAllOrderBySendingTime();
        if (!CollectionUtils.isEmpty(infoPOList)){
            // todo add cache
        }
        return infoPOList;
    }

    public boolean insertBroadcastMailInfo(AdvancedMail advancedMail) {
        BroadcastMailInfoPO po = new BroadcastMailInfoPO();
        po.setId(advancedMail.getId());
        po.setContentType(advancedMail.getContentType());
        po.setAttachmentContentFormat(advancedMail.getAttachmentContentFormat());
        po.setContentArgs(advancedMail.getContentArgs());
        po.setSendingTime(advancedMail.getSendingTime());
        int ret = infoMapper.insertFullInfo(po);
        if (checkRet(ret)) {
            // delete cache
        }
        return checkRet(ret);
    }

    public boolean checkRet(int affectRow) {
        return affectRow >= 0;
    }
}