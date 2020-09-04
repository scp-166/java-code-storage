package com.nekosighed.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

/**
 * @author lyl
 * @date 2020/9/4
 */
public class DirectoryCreate {
    public static void main(String[] args) {
        new1();
    }

    /**
     * 老方式，不推荐 <br>
     */
    public static void old() {
        File file = new File("directory1");
        // 只有 true 和 false
        file.mkdir();
        file.mkdirs();
    }

    /**
     * 推荐
     */
    public static void new1() {
        String directoryName = "directory2/sub";

        try {
            final Path directory = Files.createDirectory(Paths.get(directoryName));
            System.out.println(directory.getFileName());
        } catch (NoSuchFileException e) {
            System.out.println("父文件夹不存在");
        } catch (FileAlreadyExistsException e) {
            System.out.println("被创建的文件夹已经存在");
        } catch (IOException e) {
            System.out.println("磁盘 io 问题");
        }

        try {
            // 1. 不存在则创建
            // 2. 存在则复用
            final Path directories = Files.createDirectories(Paths.get(directoryName));
            System.out.println(directories.getFileName());
        } catch (IOException e) {
            System.out.println("磁盘 io 问题");
        }
    }
}
