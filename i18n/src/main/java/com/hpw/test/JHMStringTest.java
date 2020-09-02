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
public class JHMStringTest {

    // 指定某项参数的多种情况，特别适合用来测试一个函数在不同的参数输入的情况下的性能，只能作用在字段上
    // 使用该注解必须定义 @State 注解
    // 被@Param注解标示的参数组会一次被benchmark消费到(需要在 @Benchmark 中使用)
    // @Param(value = {"10", "50", "100"})
    // private Integer count;

    @Warmup(iterations = 5, time = 5)
    // 循环测试不要在方法体中测试
    @BenchmarkMode(Mode.SingleShotTime)
    @Measurement(iterations = 5, time = 5, batchSize = 10000000)
    @Benchmark
    public void testStringFormat(Blackhole blackhole) {
        String str;
        str = String.format("这是%s 的%s 啊%d", String.valueOf(ThreadLocalRandom.current().nextInt()), "s", ThreadLocalRandom.current().nextInt());
        blackhole.consume(str);
    }

    @Warmup(iterations = 5, time = 5)
    // 循环测试不要在方法体中测试
    @BenchmarkMode(Mode.SingleShotTime)
    @Measurement(iterations = 5, time = 5, batchSize = 10000000)
    @Benchmark
    public void testString(Blackhole blackhole) {
        String str;
        str = "这是" + String.valueOf(ThreadLocalRandom.current().nextInt()) + " 的%s " + "s" + "啊" + ThreadLocalRandom.current().nextInt();
        blackhole.consume(str);
    }


    public static void main(String[] args) throws RunnerException {
        test();
    }

    public static void test() throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(JHMStringTest.class.getSimpleName())
                // 可以去 https://jmh.morethan.io/ 可视化查看
                .result("result.json")
                .resultFormat(ResultFormatType.JSON).build();
        new Runner(opt).run();
    }
}
