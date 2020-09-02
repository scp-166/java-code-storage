package com.hpw.data;

public class Cmd {
    /**
     * 邮件初始化，分页
     */
    public final static short NET_MAIL_INIT = 2801;

    /**
     * 获取所有的邮件信息
     */
    public final static short NET_MAIL_GET_SYS_ALL = 2802;

    /**
     * 删除所有邮件信息
     */
    public final static short NET_MAIL_DEL_ALL = 2803;

    /**
     * 已读邮件信息
     */
    public final static short NET_MAIL_LOOK = 2804;

    /**
     * 领取一份邮件
     */
    public final static short NET_MAIL_GET_ONE = 2805;

    /**
     * 邮件红点显示
     */
    public final static short NET_MAIL_FOR_RED_POINT = 2806;

    /**
     * 一件领取
     */
    public final static short NET_MAIL_GET_FRIENDS_ALL = 2807;

    /**
     * 提示前端有红点显示
     */
    public final static short NET_MAIL_SHOW_RED_POINT = 2808;
}
