package com.nekosighed.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 文件读取
 *
 * @author lyl
 * @date 2020/9/4
 */
public class FileRead {
    private static final String DIR = "justFile" + File.separator;

    public static void main(String[] args) throws IOException {
        scannerRead();
        streamLine();
        justAsList();
        asBytes();
        classical();
    }

    /**
     * scanner 方式
     */
    public static void scannerRead() {
        String fileStringName = DIR + "string.txt";
        String fileSeparatorName = DIR + "separator.txt";

        // 读取行
        try (Scanner scanner = new Scanner(new FileReader(fileStringName));) {
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 根据分割符读取
        try (Scanner scanner = new Scanner(new FileReader(fileSeparatorName))) {
            // 分隔符，正则区分
            scanner.useDelimiter("\\|");
            while (scanner.hasNext()) {
                System.out.println(scanner.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 根据分隔符读取具体类型
        try (Scanner scanner = new Scanner(new FileReader(fileSeparatorName))) {
            // 分隔符，正则区分
            scanner.useDelimiter("\\|");
            if (scanner.hasNextInt()) {
                System.out.println(scanner.nextInt());
            }

            if (scanner.hasNext()) {
                System.out.println(scanner.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * java8 stream 方式 <strong>推荐，但是转化为 list 啥的要注意 {@link OutOfMemoryError}</strong>
     */
    public static void streamLine() {
        String fileName = DIR + "string.txt";

        try (final Stream<String> lines = Files.lines(Paths.get(fileName))) {
            // 随机
            lines.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (final Stream<String> lines = Files.lines(Paths.get(fileName))) {
            // 顺序，但是效率较低
            lines.forEachOrdered(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (final Stream<String> lines = Files.lines(Paths.get(fileName))) {
            // 顺序，加上并行
            lines.parallel().forEachOrdered(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (final Stream<String> lines = Files.lines(Paths.get(fileName))) {
            // 转化为 list，小心 outOfMemory
            final List<String> collect = lines.collect(Collectors.toList());
            System.out.println(collect);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 按行直接加载为 list <br>
     * <strong>要注意 {@link OutOfMemoryError}</strong>
     */
    public static void justAsList() throws IOException {
        String fileName = DIR + "string.txt";

        // 这是上面的先读取为 stream 再转为 list 的方式
        try (final Stream<String> lines = Files.lines(Paths.get(fileName))) {
            // 转化为 list，小心 outOfMemory
            final List<String> collect = lines.collect(Collectors.toList());
            System.out.println(collect);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 这是直接读取为 list 的方式
        final List<String> strings = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
        System.out.println(strings);
    }

    /**
     * 直接读取为 bytes，可以转化为 {@link String} <br>
     * <strong>同样要注意 {@link OutOfMemoryError}</strong>
     */
    public static void asBytes() throws IOException {
        String fileName = DIR + "string.txt";

        final byte[] bytes = Files.readAllBytes(Paths.get(fileName));
        System.out.println(new String(bytes, StandardCharsets.UTF_8));
    }

    /**
     * 经典管道流
     */
    public static void classical() {
        String fileName = DIR + "string.txt";
        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
