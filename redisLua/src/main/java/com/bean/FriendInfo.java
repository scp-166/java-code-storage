package com.bean;

import java.io.Serializable;

public class FriendInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long userId;

    /**
     * 0 申请中
     * 1 好友
     * 2 陌生人 用于缓存
     */
    private Integer type;

    private Long updateTime;

    public FriendInfo() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Person{" +
                "userId=" + userId +
                ", type=" + type +
                ", updateTime=" + updateTime +
                '}';
    }

    /**
     * 创建一个好友申请用户信息
     *
     * @param userId 申请者用户id
     * @return 一个申请信息
     */
    public static FriendInfo createApplyingFriendInfo(long userId) {
        FriendInfo info = new FriendInfo();
        info.userId = userId;
        info.type = 0;
        info.updateTime = System.currentTimeMillis();
        return info;
    }
}