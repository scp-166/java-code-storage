package com.hpw.bean;

import com.hpw.service.MailTemplateCache;
import com.hpw.utils.ConvertObjUtil;

import java.io.Serializable;
import java.util.List;

/**
 * Table: c_system_mail_record
 */
public class SystemMailRecordBO implements Serializable {
    /**
     * 表示实际的邮件
     */
    private Mail mail;

    /**
     * 附件奖励
     */
    private List<GameReward> gameRewardList;

    private static final long serialVersionUID = 1L;

    public Mail getMail() {
        return mail;
    }

    public void setMail(Mail mail) {
        this.mail = mail;
    }

    public List<GameReward> getGameRewardList() {
        return gameRewardList;
    }

    public void setGameRewardList(List<GameReward> gameRewardList) {
        this.gameRewardList = gameRewardList;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "SystemMailRecordBO{" +
                "mail=" + mail +
                ", gameRewardList=" + gameRewardList +
                '}';
    }
}