package com.hpw.test;

import java.util.HashMap;
import java.util.Map;

public class OtherTest {
    public static void main(String[] args) {
        Map<Integer, String> map = new HashMap<>();
        map.computeIfAbsent(2, k->{
            return "a";
        });

        System.out.println(map);

        map.computeIfAbsent(2, k->{
            return "5";
        });
        System.out.println(map);
    }

    public static void testTableUtil() {
        System.out.println(TableUtil.getTableIndex(1));
        System.out.println(TableUtil.getTableIndex(1000000000));
    }


}

class TableUtil {
    private static double spNum = 1000000D;
    /**
     * 根据userId 获取数据库表的Index
     * @param userId
     * @return
     */
    public static String getTableIndex(long userId){
        long num = userId%10000000000L;
        return getTableByIndex((int)(num/spNum));
    }

    public static String getTableByIndex(int index){
        if(index == 0)
            return "";

        return String.valueOf(index);
    }
}