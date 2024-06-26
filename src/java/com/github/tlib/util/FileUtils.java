package com.github.tlib.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * 一个简易的文件操作类
 * @since ToolboxLibrary 1.0
 * @author 3cxc
 * @version 1.1
 */
public class FileUtils {

    public static @Nullable String getDataFolder(){
        File file = new File(".");
        String path;
        try {
            path = file.getCanonicalPath() + "\\";
            return path;
        }catch (IOException e){
            return null;
        }
    }

    /**
     * 从指定的类加载器读取文件
     * @param resourcePath JAR包内相对路径
     * @param classLoader 指定的类加载器
     * @return 文件内容
     * @throws IOException 如果读取发生错误
     */
    public static String readJar(String resourcePath, @NotNull ClassLoader classLoader) throws IOException{
        // 使用ClassLoader的getResourceAsStream方法获取资源的输入流
        InputStream inputStream = classLoader.getResourceAsStream(resourcePath);

        // 使用InputStreamReader和BufferedReader读取输入流中的内容
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        // 读取文件内容
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }

        String tmp = stringBuilder.toString();
        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();
        return tmp;
    }

    /**
     * 获取指定文件夹内所有指定后缀的文件的绝对路径
     * @param folderPath 文件夹路径
     * @param suffix 要查找的文件的后缀
     * @return 包含所有指定后缀的文件的绝对路径的列表或null(找不到文件夹)
     */
    public static @NotNull List<String> getJarFilePaths(String folderPath,String suffix) {

        List<String> jarFilePaths = new ArrayList<>();
        File folder = new File(folderPath);

        if (!folder.isDirectory()) {
            return jarFilePaths;
        }

        // 获取文件夹内的所有文件
        File[] files = folder.listFiles();

        // 遍历文件列表
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(suffix)) {
                    jarFilePaths.add(file.getAbsolutePath());
                }
            }
        }

        return jarFilePaths;
    }
}
