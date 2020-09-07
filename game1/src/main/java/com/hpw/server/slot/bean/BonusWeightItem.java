package com.hpw.server.slot.bean;

/**
 * @author lyl
 * @date 2020/8/31
 */
public class BonusWeightItem extends IWeightItem<Integer> {

    public BonusWeightItem() {
    }

    public BonusWeightItem(Integer mark, int weight) {
        super(mark, weight);
    }

    @Override
    public IWeightItem<Integer> copy() {
         return new BonusWeightItem(mark, weight);
    }

    @Override
    public String toString() {
        return "BonusWeightItem{" +
                "mark=" + mark +
                ", weight=" + weight +
                '}';
    }
}
