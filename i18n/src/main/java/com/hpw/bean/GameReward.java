package com.hpw.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 原项目存有
 */
public class GameReward implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private long userId;
    private int gameFlag;
    private int itemId;
    private int itemType;
    private long itemNum;
    /**
     * 操作cmd
     */
    private int operate;
    /**
     * 奖励发出来源说明
     */
    private String des;
    /**
     * 发出奖励的时间
     */
    private Timestamp sendTime;
    /**
     * 奖励领取时间
     */
    private Timestamp getTime;
    /**
     * 0未领取 1已领取
     */
    private int state;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return this.id;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setGameFlag(int gameFlag) {
        this.gameFlag = gameFlag;
    }

    public int getGameFlag() {
        return this.gameFlag;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getItemId() {
        return this.itemId;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public int getItemType() {
        return this.itemType;
    }

    public void setItemNum(long itemNum) {
        this.itemNum = itemNum;
    }

    public long getItemNum() {
        return this.itemNum;
    }

    public void setOperate(int operate) {
        this.operate = operate;
    }

    public int getOperate() {
        return this.operate;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getDes() {
        return this.des;
    }

    public void setSendTime(Timestamp sendTime) {
        this.sendTime = sendTime;
    }

    public Timestamp getSendTime() {
        return this.sendTime;
    }

    public void setGetTime(Timestamp getTime) {
        this.getTime = getTime;
    }

    public Timestamp getGetTime() {
        return this.getTime;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return this.state;
   }

    @Override
    public String toString() {
        return "GameReward{" +
                "id=" + id +
                ", userId=" + userId +
                ", gameFlag=" + gameFlag +
                ", itemId=" + itemId +
                ", itemType=" + itemType +
                ", itemNum=" + itemNum +
                ", operate=" + operate +
                ", des='" + des + '\'' +
                ", sendTime=" + sendTime +
                ", getTime=" + getTime +
                ", state=" + state +
                '}';
    }
}

