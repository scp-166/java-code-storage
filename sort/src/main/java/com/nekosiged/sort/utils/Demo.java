package com.nekosiged.sort.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author lyl
 * @date 2020/9/17
 */
public class Demo {
    // private static final List<Integer> list = Collections.synchronizedList(new ArrayList<>());
    private static final List<Integer> list = new ArrayList<>();
    static {
        IntStream.range(0,10000).forEach(list::add);
    }
    public static void main(String[] args) {

        CountDownLatch countDownLatch = new CountDownLatch(1);
        Runnable read = ()->{
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list.forEach(System.out::println);
        };

        Runnable write = ()->{
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {

            }
            list.add(1);
        };

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 9; i++) {
            executorService.execute(read);
        }
        executorService.execute(write);

        countDownLatch.countDown();
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
