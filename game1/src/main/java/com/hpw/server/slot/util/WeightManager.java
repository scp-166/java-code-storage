package com.hpw.server.slot.util;

import com.hpw.server.slot.bean.BonusWeightItem;
import com.hpw.server.slot.bean.IWeightItem;

import java.util.*;

/**
 * 根据权重获取概率
 * https://blog.csdn.net/haha1fan/article/details/106609910
 *
 * @author lyl
 * @date 2020/8/31
 */
public class WeightManager {
    private WeightManager() {
    }

    public static final List<IWeightItem<Integer>> BONUS_WEIGHTS = new ArrayList<>();

    static {
        BONUS_WEIGHTS.add(new BonusWeightItem(10, 70));
        BONUS_WEIGHTS.add(new BonusWeightItem(20, 5));
        BONUS_WEIGHTS.add(new BonusWeightItem(15, 25));
    }

    /**
     * 线性求权重
     *
     * @param weightItems 权重单项列表
     * @param <T>         权重单项标记
     * @return 得到的权重单项, 默认给最低档
     */
    public static <T> IWeightItem<T> linearRand(List<IWeightItem<T>> weightItems) {
        // 根据权重有序
        weightItems.sort(Comparator.comparing(IWeightItem::getWeight));

        // 总权重
        int totalWeight = 0;
        for (IWeightItem<T> weightItem : weightItems) {
            totalWeight += weightItem.getWeight();
        }

        // [1, totalWeight) 随机出一个权重值
        int aimWeight = RandomUtil.nextInt(1, totalWeight);

        // 线性查找，要找到小于随机权重值的单项
        for (IWeightItem<T> weightItem : weightItems) {
            if (aimWeight <= weightItem.getWeight()) {
                return weightItem;
            }
            aimWeight -= weightItem.getWeight();
        }
        return weightItems.get(0);
    }


    public static void main(String[] args) {
        List<IWeightItem<Integer>> bonusWeights = new ArrayList<>();
        bonusWeights.add(new BonusWeightItem(10, 70));
        bonusWeights.add(new BonusWeightItem(20, 5));
        bonusWeights.add(new BonusWeightItem(15, 25));

        test1(bonusWeights);
    }

    private static void test1(List<IWeightItem<Integer>> bonusWeights) {
        Map<Integer, Integer> markAndCountMap = new HashMap<>();

        for (int i = 0; i < 100000; i++) {
            IWeightItem<Integer> item = linearRand(bonusWeights);
            if (item == null) {
                continue;
            }
            markAndCountMap.putIfAbsent(item.getMark(), 0);
            markAndCountMap.computeIfPresent(item.getMark(), (k, v) -> {
                v += 1;
                return v;
            });
        }

        System.out.println(markAndCountMap);
        List<Integer> values = new ArrayList<>();
        for (Map.Entry<Integer, Integer> item : markAndCountMap.entrySet()) {
            values.add(item.getValue());
        }
        int all = 0;
        for (Integer key : values) {
            all += key;
        }
        for (Integer key : values) {
            System.out.println("key:" + key * 1.0 / all);
        }
    }
}


