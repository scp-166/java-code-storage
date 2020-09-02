package com.hpw.service;

import com.hpw.bean.AdvancedMail;
import com.hpw.dao.BroadcastMailRecordDao;

public class MailSenderManager {
    private static class Holder {
        public static MailSenderManager Instance = new MailSenderManager();
    }

    public static MailSenderManager getInstance() {
        return Holder.Instance;
    }

    public boolean sendBroadcastMail(AdvancedMail advancedMail) {
        return BroadcastMailRecordDao.getInstance().insertRecord(advancedMail);
    }
}
