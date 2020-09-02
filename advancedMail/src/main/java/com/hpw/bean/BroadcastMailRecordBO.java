package com.hpw.bean;

import java.util.List;

public class BroadcastMailRecordBO {
    private AdvancedMail advancedMail;

    private List<GameReward> gameRewardList;

    public AdvancedMail getAdvancedMail() {
        return advancedMail;
    }

    public void setAdvancedMail(AdvancedMail advancedMail) {
        this.advancedMail = advancedMail;
    }

    public List<GameReward> getGameRewardList() {
        return gameRewardList;
    }

    public void setGameRewardList(List<GameReward> gameRewardList) {
        this.gameRewardList = gameRewardList;
    }

    @Override
    public String toString() {
        return "BroadcastMailRecordBO{" +
                "mail=" + advancedMail +
                ", gameRewardList=" + gameRewardList +
                '}';
    }
}
