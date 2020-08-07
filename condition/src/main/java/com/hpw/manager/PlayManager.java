package com.hpw.manager;

import com.hpw.myenum.PlayEnum;

import java.util.Objects;

/**
 * 玩法以及真实值转换管理
 */
public class PlayManager {
    private PlayManager() {
    }

    private static class Holder {
        public static PlayManager instance = new PlayManager();
    }

    public static PlayManager getInstance() {
        return Holder.instance;
    }


    private static final String SEPARATOR = ":";

    /**
     * 获得存储字段
     *
     * @param playEnum     玩法
     * @param storageValue 存储值,此处用字符串
     * @return 返回一个关于玩法信息的字符串，用于存储
     */
    public String getPlayStr(PlayEnum playEnum, String storageValue) {
        if (Objects.isNull(playEnum) || Objects.isNull(storageValue)) {
            // todo logger
            System.out.println("playEnum 或 storageValue 为空! playEnum: " + playEnum + " storageValue" + storageValue);
            return null;
        }
        return playEnum.getType() + SEPARATOR + storageValue;
    }

    /**
     * 获得玩法存储值的最终表示的内容
     *
     * @param playStr 玩法字符串
     * @return 玩法字符串中的真实值, 如果无法找到真实值或者玩法不匹配，则返回 {@code null}
     */
    public Object parsePlay(String playStr) {
        if (Objects.isNull(playStr)) {
            return null;
        }
        String[] ret = playStr.split(SEPARATOR);
        if (ret.length != 2) {
            // todo logger
            return null;
        }

        PlayEnum playEnum = PlayEnum.getByType(Integer.parseInt(ret[0]));
        if (Objects.isNull(playEnum)) {
            // todo logger
            return null;
        }
        String storageValue = ret[1];

        switch (playEnum) {
            case JACK_POT:
                int poolNum = Integer.parseInt(storageValue);
                // fixme {@author lyl} 获得库存值，此处随便写
                return poolNum * 1000;
            default:
                return null;
        }
    }
}
