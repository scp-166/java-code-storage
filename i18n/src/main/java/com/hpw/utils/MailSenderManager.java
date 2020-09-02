package com.hpw.utils;

import com.hpw.bean.GameReward;
import com.hpw.bean.Mail;
import com.hpw.bean.SystemMailRecordBO;
import com.hpw.dao.SystemMailRecordDao;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 邮件发送
 */
public class MailSenderManager {
    private static class Holder {
        public static MailSenderManager instance = new MailSenderManager();
    }

    public static MailSenderManager getInstance() {
        return Holder.instance;
    }

    private AtomicInteger count = new AtomicInteger(0);

    /**
     * 发送系统邮件
     *
     * @param mail         邮件
     * @param languageType 语言类型
     * @param args         邮件内容参数
     * @param rds          游戏奖励
     * @return 是否发送成功
     */
    public boolean sendSystemMail(Mail mail, int languageType, Object[] args, List<GameReward> rds) {
        if (Objects.isNull(mail)) {
            return false;
        }

        SystemMailRecordBO bo = new SystemMailRecordBO();
        bo.setMail(mail);
        bo.setGameRewardList(rds);

        MailConvertUtil.convertI18nMailCompulsive(mail, languageType, mail.getContentType());

        if (Objects.nonNull(args)) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                builder.append(args[i]);
                if (i != args.length - 1) {
                    builder.append(";");
                }
            }
            mail.setContentArgs(builder.toString());
            mail.contentFormat(args);
        }

        return SystemMailRecordDao.getInstance().insertFullRecord(bo);
    }
}
