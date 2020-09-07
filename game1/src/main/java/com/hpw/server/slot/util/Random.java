package com.hpw.server.slot.util;

/**
 * 随机函数
 *
 * @author lyl
 * @date 2020/8/31
 */
public class Random {
    private int seed;

    public Random() {
    }

    public Random(int seed) {
        this.seed = seed;
    }

    /**
     * 一个一次性随机函数，不具有连续性
     *
     * @return 根据随机种子算出一个随机数
     */
    public int getInt() {
        seed ^= (seed << 21);
        seed ^= (seed >>> 3);
        seed ^= (seed << 4);
        return seed & Integer.MAX_VALUE;
    }

    public static void main(String[] args) {
        Random random = new Random(20);
        System.out.println(random.getInt());
        System.out.println(random.getInt());
    }
}
