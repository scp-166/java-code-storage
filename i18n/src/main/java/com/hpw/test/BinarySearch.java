package com.hpw.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BinarySearch {
    public static void main(String[] args) {
        // writeText(new File("binary.txt"), 100, 0,50);
        List<Integer> integerList = readFromFile(new File("binary.txt"), 100);
        integerList.sort(Integer::compareTo);
        System.out.println(integerList);
        System.out.println("+++++++++++++++++");
        int index = binarySearch2(integerList, 10);
        System.out.println(index);
        System.out.println(integerList.get(index));

    }

    /**
     * 二分查找最后一个等于指定值的值
     */
    public static <E extends Comparable<E>> int binarySearch(List<E> list, E item) {
        int low = 0;
        int high = list.size() - 1;
        int mid = 0;
        while (low <= high) {
            mid = low + ((high - low) >> 1);
            if (list.get(mid).compareTo(item) > 0) {
                high = mid - 1;
            } else if (list.get(mid).compareTo(item) < 0) {
                low = mid + 1;
            } else {
                if (mid == list.size() - 1 || (item.compareTo(list.get(mid + 1)) != 0)) {
                    break;
                } else {
                    low = mid + 1;
                }
            }
        }
        return mid;
    }

    /**
     * 二分查找最后一个小于等于指定值的值
     */
    public static <E extends Comparable<E>> int binarySearch2(List<E> list, E item) {
        int low = 0;
        int high = list.size() - 1;
        int mid = 0;
        while (low <= high) {
            mid = low + ((high - low) >> 1);
            if (list.get(mid).compareTo(item) > 0) {
                high = mid - 1;
            } else {
                // mid 所在元素为最后一个元素，那么所有元素都小于等于指定值
                // 如果 mid+1 的元素大于指定值，那么 mid 就是小于等于
                if ((mid == list.size() - 1) || (item.compareTo(list.get(mid + 1)) < 0)) {
                    break;
                } else {
                    low = mid + 1;
                }

            }
        }
        return mid;
    }


    public static void writeText(File file, int size, int origin, int bound) {
        try (OutputStream outputStream = new FileOutputStream(file)) {
            for (int i = 0; i < size; i++) {
                outputStream.write(String.valueOf(ThreadLocalRandom.current().nextInt(origin, bound)).getBytes());
                if (i != size - 1) {
                    outputStream.write(",".getBytes());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Integer> readFromFile(File file, int size) {
        List<Integer> dataList = new ArrayList<>(size);
        try {
            List<String> contentList = Files.readAllLines(Paths.get(file.getName()));
            String str = contentList.get(0);
            String[] split = str.split(",");
            for (int i = 0; i < size; i++) {
                dataList.add(Integer.parseInt(split[i]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataList;
    }
}
