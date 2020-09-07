package com.hpw.server.slot.constant;

import com.hpw.server.slot.bean.SlotItem;
import com.hpw.server.slot.util.SlotConfigCache;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lyl
 * @date 2020/8/27
 */
public class XiaoyaogeConstant {
    public static final String SEPARATOR = ":";

    /**
     * 列长度
     */
    public static final int COLUMN_SIZE = 5;

    /**
     * 行长度
     */
    public static final int ROW_SIZE = 3;

    public static Map<Integer, String> BASIC_PATTERN_MAP = new HashMap<>();

    public static Map<Integer, String> SPECIAL_PATTERN_MAP = new HashMap<>();

    public static Map<Integer, String> ALL_PATTERN_MAP = new HashMap<>();

    /**
     * 普通图案奖励倍率 {slotItemId: {连续次数: 倍率}}
     */
    public static Map<Integer, Map<Integer, Integer>> NORMAL_PATTERN_REWARD_TIME_MAP = new HashMap<>(8);

    /**
     * WILD图案奖励倍率 {连续次数: 倍率}}
     */
    public static Map<Integer, Integer> WILD_REWARD_TIME_MAP = new HashMap<>(1);

    public static Integer WILD_ID;

    public static Integer WILD_X2_ID;

    public static Integer JACKPOT_ID;

    public static Integer BONUS_ID;


    public static Map<Integer, String> WILDS_MAP = new HashMap<>(2);

    public static void parse() {
        for (SlotItem slotItem : SlotConfigCache.SLOT_ITEMS) {
            if (slotItem.getType() == 0) {
                BASIC_PATTERN_MAP.put(slotItem.getId(), slotItem.getName());
                ALL_PATTERN_MAP.put(slotItem.getId(), slotItem.getName());
                NORMAL_PATTERN_REWARD_TIME_MAP.putIfAbsent(slotItem.getId(), new HashMap<>(3));
                NORMAL_PATTERN_REWARD_TIME_MAP.computeIfPresent(slotItem.getId(), (k, v) -> {
                    String rewardTime = slotItem.getRewardTime();
                    if (rewardTime != null) {
                        String[] rewardTimesArr = rewardTime.split(";");
                        for (String rewardTimeItem : rewardTimesArr) {
                            String[] item = rewardTimeItem.split(",");
                            v.put(Integer.parseInt(item[0]), Integer.parseInt(item[1]));
                        }
                    }
                    return v;
                });
            }

            if (slotItem.getType() == 1) {
                WILD_ID = slotItem.getId();
                WILDS_MAP.put(slotItem.getId(), slotItem.getName());
                ALL_PATTERN_MAP.put(slotItem.getId(), slotItem.getName());

                String rewardTime = slotItem.getRewardTime();
                if (rewardTime != null) {
                    String[] rewardTimesArr = rewardTime.split(";");
                    for (String rewardTimeItem : rewardTimesArr) {
                        String[] item = rewardTimeItem.split(",");
                        WILD_REWARD_TIME_MAP.put(Integer.parseInt(item[0]), Integer.parseInt(item[1]));
                    }
                }
            }

            if (slotItem.getType() == 2) {
                WILD_X2_ID = slotItem.getId();
                WILDS_MAP.put(slotItem.getId(), slotItem.getName());
                ALL_PATTERN_MAP.put(slotItem.getId(), slotItem.getName());
            }

            if (slotItem.getType() == 3) {
                JACKPOT_ID = slotItem.getId();
                SPECIAL_PATTERN_MAP.put(slotItem.getId(), slotItem.getName());
                ALL_PATTERN_MAP.put(slotItem.getId(), slotItem.getName());
            }

            if (slotItem.getType() == 4) {
                BONUS_ID = slotItem.getId();
                SPECIAL_PATTERN_MAP.put(slotItem.getId(), slotItem.getName());
                ALL_PATTERN_MAP.put(slotItem.getId(), slotItem.getName());
            }
        }
    }
}
