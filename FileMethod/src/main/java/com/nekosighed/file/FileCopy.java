package com.nekosighed.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

/**
 * @author lyl
 * @date 2020/9/4
 */
public class FileCopy {
    private static final String DIR = "justFile" + File.separator;

    public static void main(String[] args) {
        copy();
        moveAndRename1();
        moveAndRename2();
    }

    public static void copy() {
        Path fromPath = Paths.get(DIR + "separator.txt");
        Path toPath = Paths.get(DIR + "copy.txt");

        try {
            // 默认可能会抛出多异常
            Files.copy(fromPath, toPath);
        } catch (FileAlreadyExistsException e) {
            System.out.println("目标文件已存在");
        } catch (NoSuchFileException e) {
            System.out.println("源文件不存在");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            // 添加 覆盖写 选项, 可以去除异常
            Files.copy(fromPath, toPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (NoSuchFileException e) {
            System.out.println("源文件不存在");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            // 多种选项，后面那个是 复制源文件其他信息
            Files.copy(fromPath, toPath, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
        } catch (NoSuchFileException e) {
            System.out.println("源文件不存在");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@link Files#move(Path, Path, CopyOption...)} 类似 linux 中的 move
     */
    public static void moveAndRename1() {

        Path fromPath = Paths.get(DIR + "source.txt");
        Path toPath = Paths.get(DIR + "to.txt");

        try {
            Files.createFile(fromPath);
            Files.delete(toPath);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        try {
            Files.move(fromPath, toPath, StandardCopyOption.REPLACE_EXISTING);
        }
        // 可以被选项处理
        // catch (FileAlreadyExistsException e) {
        //     System.out.println("目标文件已存在");
        // }
        catch (NoSuchFileException e) {
            System.out.println("源文件不存在");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@link Files#move(Path, Path, CopyOption...)} 类似 linux 中的 move
     */
    public static void moveAndRename2() {

        Path fromPath = Paths.get(DIR + "source.txt");
        Path toPath = Paths.get(DIR + "to.txt");

        try {
            Files.createFile(fromPath);
            Files.delete(toPath);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            // {@link Path#resolve(String)} 是将调用path和参数路径拼接在一起
            // {@link Path#resolveSibling(String)} 是将调用path的 <strong>父路径</strong> 和参数路径拼接在一起
            // 比自己拼接方式更好
            Files.move(fromPath, fromPath.resolveSibling("to.txt"), StandardCopyOption.REPLACE_EXISTING);
        } // 可以被选项处理
        // catch (FileAlreadyExistsException e) {
        //     System.out.println("目标文件已存在");
        // }
        catch (NoSuchFileException e) {
            System.out.println("源文件不存在");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
