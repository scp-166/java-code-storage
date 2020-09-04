package com.nekosighed.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Comparator;
import java.util.stream.Stream;

/**
 * @author lyl
 * @date 2020/9/4
 */
public class FileDelete {
    public static void main(String[] args) throws IOException {
        normalDelete1();
        System.out.println("===============");
        normalDelete2();
    }

    /**
     * 简单的创建文件
     */
    private static void createMoreFiles() throws IOException {
        Files.createDirectories(Paths.get("data\\test1\\test2\\test3\\test4\\test5\\"));
        Files.write(Paths.get("data\\test1\\test2\\test2.log"), "hello".getBytes());
        Files.write(Paths.get("data\\test1\\test2\\test3\\test3.log"), "hello".getBytes());
    }

    /**
     * 删除文件或者 <strong>空文件夹</strong>
     */
    public static void deleteFileOrEmptyDir() {
        // 1. 传统 io
        File file = new File("text.txt");
        // 返回值 boolean，无法告知什么原因
        // file.delete();

        // 返回值 无，不会知道是否成功
        // file.deleteOnExit();

        // 2. nio
        try {
            // 返回值 无
            Files.delete(Paths.get("text.txt"));
        } catch (NoSuchFileException e) {
            System.out.println("不存在对应文件");
        } catch (DirectoryNotEmptyException e) {
            System.out.println("文件夹中包含内容");
        } catch (IOException e) {
            System.out.println("磁盘 io 问题");
        }

        try {
            // 文件不存在，返回 false
            // true，文件删除或者空文件夹删除成功
            final boolean b = Files.deleteIfExists(Paths.get("text.txt"));
        } catch (DirectoryNotEmptyException e) {
            System.out.println("文件夹中包含内容");
        } catch (IOException e) {
            System.out.println("磁盘 io 问题");
        }
    }

    /**
     * 递归方式，有条件删除
     */
    public static void normalDelete1() {
        try {
            createMoreFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 设置根路径(只会从子文件夹开始处理
        String rootPathName = "data";

        try {
            // 从尾部开始删除
            Files.walkFileTree(Paths.get(rootPathName), new SimpleFileVisitor<Path>() {
                // 处理文件
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    System.out.println("访问到文件: " + file.getFileName() + "属性为: " + attrs);
                    Files.delete(file);
                    System.out.println("删除文件: " + file);
                    return FileVisitResult.CONTINUE;
                }

                // 处理文件夹， post 表示是在 visitFile 后进行的处理
                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    System.out.println("访问到文件夹: " + dir.getFileName());
                    Files.delete(dir);
                    System.out.println("删除文件夹: " + dir.getFileName());
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void normalDelete2() {
        try {
            createMoreFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 设置根路径(只会从子文件夹开始处理
        String rootPathName = "data";

        try {
            final Stream<Path> walk = Files.walk(Paths.get(rootPathName));
            // 利用的是字符串的排序规则; 相同父目录的字符串，短的都在长的前面
            // 此处进行倒叙排序，让长的排在前面
            walk.sorted(Comparator.reverseOrder())
                    .forEach(FileDelete::delete);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void delete(Path path) {
        try {
            Files.delete(path);
            System.out.println("删除文件或者空文件夹: " + path.getFileName() + " 成功");
        } catch (IOException e) {
            System.out.println("删除文件or遇到了非空文件夹: " + path.getFileName() + ", 删除失败");
            e.printStackTrace();
        }
    }


    /**
     * 传统递归删除
     *
     */
    private static void classicalDelete(File file) {

        File[] list = file.listFiles();
        if (list != null) {
            //先去递归删除子文件夹及子文件
            for (File temp : list) {
                classicalDelete(temp);
            }
        }

        //再删除自己本身的文件夹
        if (file.delete()) {
            System.out.printf("删除成功 : %s%n", file);
        } else {
            System.err.printf("删除失败 : %s%n", file);
        }
    }
}
