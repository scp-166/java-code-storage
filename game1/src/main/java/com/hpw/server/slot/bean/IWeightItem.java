package com.hpw.server.slot.bean;

/**
 * 权重单项
 *
 * @param <T> 标记
 * @author lyl
 * @date 2020/8/31
 */
public abstract class IWeightItem<T> {
    /**
     * 命中标记，比如奖励
     */
    protected T mark;

    /**
     * 权重值
     */
    protected int weight;

    public IWeightItem() {
    }

    public IWeightItem(T mark, int weight) {
        this.mark = mark;
        this.weight = weight;
    }

    /**
     * 深拷贝
     * @return 深拷贝
     */
    abstract public IWeightItem<T> copy();

    public T getMark() {
        return mark;
    }

    public void setMark(T mark) {
        this.mark = mark;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * toString()
     *
     * @return 可视化表示
     */
    @Override
    public abstract String toString();
}
