package com.nekosiged.sort.utils;

import org.apache.commons.lang3.time.StopWatch;

import java.util.*;

/**
 * @author lyl
 * @date 2020/9/8
 */
public class InsertSort {
    public static void main(String[] args) {
        int size = 200;
        int loop = 10;

        Random[] randoms = new Random[loop];
        for (int i = 0; i < loop; i++) {
            randoms[i] = new Random(i + 2000);
        }

        for (int i = 0; i < loop; i++) {
            List<Mail> mails = new ArrayList<>(size);
            for (int j = 0; j < size; j++) {
                mails.add(new Mail(randoms[i].nextInt()));
            }

            mails.sort(Comparator.comparing(Mail::getId));
            mails.add(new Mail(randoms[i].nextInt()));

            StopWatch watch = new StopWatch();
            watch.start();
            // sortAdvanced(mails);
            sort(mails);
            // mails.sort(Comparator.comparing(Mail::getId));
            System.out.println(watch.getTime());
            // display(mails);
            // System.out.println("=================================");
        }
    }

    public static void display(List<Mail> mailList) {
        for (Mail mail : mailList) {
            System.out.println(mail);
        }
    }

    /**
     * 普通插入排序 <br>
     * 从第二个元素开始，和左边的排序完成的列表进行比较，一旦发现左边比当前元素小，说明当前元素可以直接作为排序完成的列表最大值
     *
     * @param mailList
     */
    public static void sort(List<Mail> mailList) {
        int checkIndex;
        Mail temp;
        // 从第二个元素开始第一轮
        for (int i = 1; i < mailList.size(); i++) {
            temp = mailList.get(i);
            // 从有序边界开始
            checkIndex = i - 1;
            // 遍历左边排序列，给 maiList.get(checkIndex) 找到合适的位置
            while (checkIndex >= 0 && temp.id < mailList.get(checkIndex).id) {
                // 将小于目标值的元素一直往前排(temp所在位置一般会被挪位置覆盖，所以之前要把 temp 拿出来
                mailList.set(checkIndex + 1, mailList.get(checkIndex));
                checkIndex--;
            }
            // 找到插入位置，放入原有元素
            mailList.set(checkIndex + 1, temp);
        }
    }

    /**
     * 普通插入排序 <br>
     * 从第二个元素开始，和左边的排序完成的列表进行比较，一旦发现左边比当前元素小，说明当前元素可以直接作为排序完成的列表最大值
     *
     * @param mailList
     */
    public static void sortAdvanced(List<Mail> mailList) {
        int boundaryIndex;
        Mail temp;
        // 从第二个元素开始第一轮
        for (int i = 1; i < mailList.size(); i++) {
            temp = mailList.get(i);
            // 有序边界
            boundaryIndex = i - 1;
            // 遍历左边排序列，找到最后一个大于目标值的位置
            // int index = binarySearchLastBigger(mailList, i, temp);
            int index = binarySearchFirstBigger(mailList, i , temp);
            // 未找到，则将当前元素作为有序的一部分，即跳过
            // 找到了，则对 index 右边进行排列
            if (index >= 0) {
                while (boundaryIndex >= index) {
                    mailList.set(boundaryIndex + 1, mailList.get(boundaryIndex));
                    boundaryIndex--;
                }
                // 找到插入位置，放入原有元素
                mailList.set(boundaryIndex + 1, temp);
            }


        }

        // display(mailList);
    }


    /**
     * 二分查找第一个比目标大的数值
     */
    private static int binarySearchFirstBigger(List<Mail> subList, Mail targetMail) {
        int left = 0;
        int right = subList.size() - 1;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            if (subList.get(mid).id >= targetMail.id) {
                // 如果只剩一个或者左边元素比目标还要小，则当前元素为第一个最大的
                if ((mid == 0) || (subList.get(mid - 1).id < targetMail.id)) {
                    return mid;
                } else {
                    right = mid - 1;
                }
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }

    /**
     * 二分查找第一个比目标大的数值
     */
    private static int binarySearchFirstBigger(List<Mail> sourceList, int n,  Mail targetMail) {
        int left = 0;
        int right = n - 1;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            if (sourceList.get(mid).id >= targetMail.id) {
                // 如果只剩一个或者左边元素比目标还要小，则当前元素为第一个最大的
                if ((mid == 0) || (sourceList.get(mid - 1).id < targetMail.id)) {
                    return mid;
                } else {
                    right = mid - 1;
                }
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }

    private static class Mail {
        long id;

        public Mail() {
        }

        public Mail(long id) {
            this.id = id;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "Mail{" +
                    "id=" + id +
                    '}';
        }
    }
}
