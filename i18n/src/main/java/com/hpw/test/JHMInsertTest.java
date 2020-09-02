package com.hpw.test;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

// Mode 选项
// Throughput：整体吞吐量，每秒执行了多少次调用，单位为 ops/time
// AverageTime：用的平均时间，每次操作的平均时间，单位为 time/op
// SampleTime：随机取样，最后输出取样结果的分布
// SingleShotTime：只运行一次，往往同时把 Warmup 次数设为 0，用于测试冷启动时的性能
// All：上面的所有模式都执行一次
// @BenchmarkMode(Mode.AverageTime)
// 预热
// iterations：预热的次数
// time：每次预热的时间
// timeUnit：时间的单位，默认秒
// batchSize：批处理大小，每次操作调用几次方法
// @Warmup(iterations = 2, time = 3)
// 实际调用方法所需要配置的一些基本测试参数
// @Measurement(iterations = 3, time = 5)
// 每个进程中的测试线程
@Threads(4)
// 进行 fork 的次数
@Fork(2)
// 指定一个对象的作用范围
// Scope.Benchmark：所有测试线程共享一个实例，测试有状态实例在多线程共享下的性能
// Scope.Group：同一个线程在同一个 group 里共享实例
// Scope.Thread：默认的 State，每个测试线程分配一个实例
@State(value = Scope.Benchmark)
// 为统计结果的时间单位
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class JHMInsertTest {

    // 指定某项参数的多种情况，特别适合用来测试一个函数在不同的参数输入的情况下的性能，只能作用在字段上
    // 使用该注解必须定义 @State 注解
    // 被@Param注解标示的参数组会一次被benchmark消费到(需要在 @Benchmark 中使用)
    // @Param(value = {"10", "50", "100"})
    // private Integer count;

    static List<Long> data = readFromFile(new File("data.txt"), 100000);

    @Warmup(iterations = 5, time = 2)
    // 循环测试不要在方法体中测试
    @BenchmarkMode(Mode.SingleShotTime)
    @Measurement(iterations = 5, time = 2, batchSize = 1000)
    @Benchmark
    public void testInsert(Blackhole blackhole) {
        List<Long> myData = new ArrayList<>(data);
        List<Long> ret = selectInsert(myData, ThreadLocalRandom.current().nextLong());
        // List<Long> ret = insertAndSort(myData, ThreadLocalRandom.current().nextLong());
        // List<Long> ret = justInsert(myData, ThreadLocalRandom.current().nextLong());
        // List<Long> ret = justInsertAdvanced(myData, ThreadLocalRandom.current().nextLong());
        blackhole.consume(ret);
    }

    public List<Long> insertAndSort(List<Long> data, Long item) {
        data.add(item);
        data.sort(((o1, o2) -> {
            if (o1 > o2) {
                return 1;
            } else if (o1.equals(o2)) {
                return 0;
            } else {
                return -1;
            }
        }));
        return data;
    }

    public List<Long> justInsert(List<Long> data, Long item) {
        if (data.size() == 0) {
            data.add(item);
            return data;
        }
        Long target;
        int temp;
        for (int i = 1; i < data.size(); i++) {
            target = data.get(i);
            temp = i - 1;
            while (temp >= 0 && target < data.get(temp)) {
                data.set(temp + 1, data.get(temp));
                temp -= 1;
            }
            data.set(temp + 1, target);
        }
        return data;
    }

    public List<Long> justInsertAdvanced(List<Long> data, Long item) {
        if (data.size() == 0) {
            data.add(item);
            return data;
        }
        Long target;
        int temp;
        int index;
        for (int i = 1; i < data.size(); i++) {
            target = data.get(i);
            temp = i - 1;
            index = Collections.binarySearch(data.subList(0, i), target);
            while (index > 0 && index < temp) {
                data.set(temp + 1, data.get(temp));
                temp -= 1;
            }
            data.set(temp + 1, target);
        }
        return data;
    }

    public static List<Long> selectInsert(List<Long> data, Long item) {
        //最小值索引
        int index = 0;
        //交换中间值
        long mid = 0;
        for (int i = 0; i < data.size() - 1; i++) {
            index = i;
            for (int j = i; j < data.size() - 1; j++) {
                if (data.get(j + 1) < data.get(index)) {
                    index = j + 1;
                }
            }
            mid = data.get(i);
            data.set(i, data.get(index));
            data.set(index, mid);
        }
        return data;
    }


    public static void main(String[] args) throws RunnerException {
        test();
    }

    public static void test() throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(JHMInsertTest.class.getSimpleName())
                // 可以去 https://jmh.morethan.io/ 可视化查看
                .result("result.json")
                .resultFormat(ResultFormatType.JSON).build();
        new Runner(opt).run();
    }

    public static void writeText(File file, int size) {
        try (OutputStream outputStream = new FileOutputStream(file)) {
            for (int i = 0; i < size; i++) {
                outputStream.write(String.valueOf(ThreadLocalRandom.current().nextLong()).getBytes());
                if (i != size - 1) {
                    outputStream.write(",".getBytes());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Long> readFromFile(File file, int size) {
        List<Long> dataList = new ArrayList<>(size);
        try {
            List<String> contentList = Files.readAllLines(Paths.get(file.getName()));
            String str = contentList.get(0);
            String[] split = str.split(",");
            for (int i = 0; i < size; i++) {
                dataList.add(Long.parseLong(split[i]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataList;
    }

}
