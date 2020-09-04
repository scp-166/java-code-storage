package com.nekosighed.file;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

/**
 * 文件创建和写入
 *
 * @author lyl
 * @date 2020/9/4
 */
public class FileCreateAndWrite {
    private static final String DIR = "justFile" + File.separator;

    public static void main(String[] args) {
        createAndWrite();
        createAndWrite2();
        createFile();
        createAndWrite34();

        createAndWrite5();
        createAndWrite6();
    }

    /**
     * 需要手动创建流，不推荐
     * {@link Path} 和 {@link Paths} 是 jdk7 开始提供的 <br>
     */
    @Deprecated
    public static void createAndWrite() {
        Path path = Paths.get(DIR + "text1.txt");

        // 对文件操作，都会先创建文件

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            writer.write("覆盖写1");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (final BufferedWriter writer = Files.newBufferedWriter(path,
                StandardCharsets.UTF_8,
                // 有选项
                StandardOpenOption.APPEND)) {
            writer.write("追加写1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * jdk7 提供 {@link Files#write(Path, byte[], OpenOption...)}
     * 不显式创建流，<strong>推荐</strong>
     */
    public static void createAndWrite2() {
        Path path = Paths.get(DIR + "text2.txt");

        // 对文件操作，都会先创建文件
        try {
            Files.write(path, "覆盖写2".getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Files.write(path, "追加写2".getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createFile() {
        File file = new File(DIR + "text.txt");

        try {
            if (file.createNewFile()) {
                System.out.println("创建成功");
            } else {
                System.out.println("创建失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * FileWriter
     */
    public static void createAndWrite34() {
        File file1 = new File(DIR + "text3.txt");
        File file2 = new File(DIR + "text4.txt");

        // 覆盖写
        try (FileWriter writer = new FileWriter(file1)) {
            writer.write("覆盖写3");
            writer.append("覆盖写3");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 重载方法实现追加写
        try (FileWriter writer = new FileWriter(file2, true)) {
            writer.write("追加写3");
            writer.append("追加写3");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * jdk5 的 PrintWriter <br>
     * 其中 {@link PrintWriter#println()}可以实现<strong>单行写入</strong>
     */
    public static void createAndWrite5() {
        String fileName = DIR + "text5.txt";

        // jdk10 后，可以用 StandardCharsets.UTF_8
        try (PrintWriter writer = new PrintWriter(fileName, "UTF-8")) {
            writer.write("全文写入");
            writer.print(false);

            writer.println();

            writer.println("写后换行");
            writer.print(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 管道流，比较灵活
     */
    public static void createAndWrite6() {
        String fileName = DIR + "text6.txt";

        try (FileOutputStream fos = new FileOutputStream(fileName);
             OutputStreamWriter osw = new OutputStreamWriter(fos);
             BufferedWriter bw = new BufferedWriter(osw)) {
            bw.write("创建文件");
            // 需要手动刷新
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
