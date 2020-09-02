package com.hpw.utils;

import com.hpw.bean.AdvancedMail;
import com.hpw.bean.BroadcastMailInfoPO;

import java.util.Comparator;

/**
 * 生成多种 comparator
 * todo 下面多个类都使用 id 自然升序的，其实是可以向上抽象个接口出来，但是 PO 类我不想动他，所以就没弄了
 */
public class ComparatorUtil {
    /**
     * 根据 {@link AdvancedMail}的id字段、自然升序
     *
     * @param <E>
     */
    public static class MailIdAscComparator<E extends AdvancedMail> implements Comparator<E> {

        @Override
        public int compare(E o1, E o2) {
            if (o1.getId() > o2.getId()) {
                return 1;
            } else if (o1.getId().equals(o2.getId())) {
                return 0;
            } else {
                return -1;
            }
        }
    }

    /**
     * 根据 {@link BroadcastMailInfoPO}的id字段自然升序
     *
     * @param <E>
     */
    public static class BroadcastMailInfoPOIdAscComparator<E extends BroadcastMailInfoPO> implements Comparator<E> {
        @Override
        public int compare(E o1, E o2) {
            if (o1.getId() > o2.getId()) {
                return 1;
            } else if (o1.getId().equals(o2.getId())) {
                return 0;
            } else {
                return -1;
            }
        }
    }
}
