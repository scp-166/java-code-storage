package com.hpw.server.slot.util;

import com.hpw.server.slot.bean.SlotItem;
import com.hpw.server.slot.constant.XiaoyaogeConstant;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author lyl
 * @date 2020/8/26
 */
public class RollerGenerator {

    /**
     * 混合图案id
     */
    public static List<Integer> PATTERNS_LIST = new ArrayList<>();

    /**
     * 除了 jackpot 和 bonus 的图案id
     */
    public static List<Integer> PATTERNS_LIST_WITHOUT_SPECIAL_ID = new ArrayList<>();

    static {
        PATTERNS_LIST.addAll(XiaoyaogeConstant.ALL_PATTERN_MAP.keySet());
        PATTERNS_LIST_WITHOUT_SPECIAL_ID.addAll(PATTERNS_LIST);
        PATTERNS_LIST_WITHOUT_SPECIAL_ID.removeAll(XiaoyaogeConstant.SPECIAL_PATTERN_MAP.keySet());
    }


    /**
     * 生成轴
     * todo {@author lyl} 后面是策划配置，此时随机生成
     *
     * @param rowNum 每列的行数
     * @return 最终的图案
     */
    public static List<List<Integer>> buildRoller(int[] rowNum) {
        List<List<Integer>> retList = new ArrayList<>(XiaoyaogeConstant.COLUMN_SIZE);

        List<Integer> rowList;
        for (int i = 0; i < XiaoyaogeConstant.COLUMN_SIZE; i++) {
            rowList = new ArrayList<>(rowNum[i]);
            for (int j = 0; j < rowNum[i]; j++) {
                // jackpot bonus 只在 1,3列
                if (i == 0 || i == 2 || i == 4) {
                    while (true) {
                        // 随机拿一个
                        Integer patternId = PATTERNS_LIST.get(ThreadLocalRandom.current().nextInt(1, PATTERNS_LIST.size()));
                        boolean end = false;
                        for (Integer pId : XiaoyaogeConstant.SPECIAL_PATTERN_MAP.keySet()) {
                            if (pId.equals(patternId)) {
                                System.out.println("第 " + i + "行" + " 过滤了 " + patternId);

                            } else {
                                rowList.add(patternId);
                                end = true;
                            }
                        }
                        if (end) {
                            break;
                        }
                    }
                } else {
                    rowList.add(PATTERNS_LIST_WITHOUT_SPECIAL_ID
                            .get(ThreadLocalRandom.current().nextInt(1, PATTERNS_LIST_WITHOUT_SPECIAL_ID.size() - 1)));
                }

            }
            retList.add(rowList);
        }

        return retList;
    }
}
