package com.hpw.server.slot.util;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author lyl
 * @date 2020/8/27
 */
public class RandomUtil {
    private RandomUtil() {
    }

    /**
     * 获得随机正整数
     *
     * @return 返回 [0, Integer.MAX_VALUE) 之间的随机整数
     */
    public static int nextPositiveInt() {
        return ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE);
    }

    /**
     * 获得随机正整数
     *
     * @param bound 范围上限
     * @return 返回 [0, bound) 之间的随机整数
     */
    public static int nextPositiveInt(int bound) {
        return ThreadLocalRandom.current().nextInt(0, bound);
    }


    /**
     * 获得随机整数
     *
     * @return 返回 [Integer.MIN_VALUE, Integer.MAX_VALUE) 之间的随机整数
     */
    public static int nextInt() {
        return ThreadLocalRandom.current().nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /**
     * 获得随机整数
     *
     * @param bound 范围上限
     * @return 返回 [Integer.MIN_VALUE, bound) 之间的随机整数
     */
    public static int nextInt(int bound) {
        return ThreadLocalRandom.current().nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /**
     * 获得随机整数
     *
     * @param origin 范围下限
     * @param bound  范围上限
     * @return 返回 [origin, bound) 之间的随机整数
     */
    public static int nextInt(int origin, int bound) {
        return ThreadLocalRandom.current().nextInt(origin, bound);
    }


}
