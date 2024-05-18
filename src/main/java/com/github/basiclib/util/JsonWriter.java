package com.github.basiclib.util;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * JSON文件IO操作类
 * @since Plugin Basic Lib 1.0
 * @author 3cxc
 * @version 1.0
 */
public class JsonWriter {

    /**
     * 单位缩进字符串。
     */
    private static final String SPACE = "   ";

    /**
     * 返回指定次数的缩进字符串。每一次缩进三个空格，即SPACE。
     *
     * @param number 缩进次数。
     * @return 指定缩进次数的字符串。
     */
    private static @NotNull String indent(int number) {
        return SPACE.repeat(Math.max(0, number));
    }

    /**
     * 返回格式化JSON字符串。
     *
     * @param json 未格式化的JSON字符串。
     * @return 格式化的JSON字符串。
     */
    public static @NotNull String formatJson(@NotNull String json) {
        StringBuilder result = new StringBuilder();

        int length = json.length();
        int number = 0;
        char key;

        //遍历输入字符串。
        for (int i = 0; i < length; i++) {
            //获取当前字符。
            key = json.charAt(i);
            //如果当前字符是前方括号/前花括号做如下处理
            if ((key == '[') || (key == '{')) {
                //如果前面有字符,且字符为“：”，打印：换行和缩进字符字符串
                if ((i - 1 > 0) && (json.charAt(i - 1) == ':')) {
                    result.append('\n');
                    result.append(indent(number));
                }
                //打印当前字符
                result.append(key);
                //前方括号/前花括号的后面必须换行
                result.append('\n');
                // 每出现一次前方括号/前花括号,缩进次数增加一次
                number++;
                result.append(indent(number));
                continue;
            }
            // 如果当前字符是后方括号/后花括号做如下处理
            if ((key == ']') || (key == '}')) {
                //后方括号/后花括号的前面必须换行
                result.append('\n');
                //每出现一次后方括号/后花括号,缩进次数减少一次
                number--;
                result.append(indent(number));
                //打印字符。
                result.append(key);
                //如果当前字符后面还有字符,且字符不为“，”,那么就换行
                if (((i + 1) < length) && (json.charAt(i + 1) != ',')) {
                    result.append('\n');
                }
                continue;
            }

            //如果当前字符是逗号,逗号后面换行并缩进,缩进次数不变
            if ((key == ',')) {
                result.append(key);
                result.append('\n');
                result.append(indent(number));
                continue;
            }
            //打印字符
            result.append(key);
        }

        return result.toString();
    }

    /**
     * 通用的JSON文件写入方法
     * @param path 文件路径
     * @param map 要写入的数据
     * @exception IOException 如果写入失败或找不到文件
     */
    public static void writeJson(String path, Map<String,Object> map)throws IOException{
        File file = new File(path);
        JSONObject object = new JSONObject(map);
        String jsonString = formatJson(object.toString()); //格式化JSON字符串
        Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
        writer.write(jsonString);
        writer.flush();
        writer.close();
    }

    /**
     * 通用的JSON文件读取方法
     * @param path 文件路径
     * @return JSON字符串
     * @throws IOException 如果写入失败或找不到文件
     */
    public static JSONObject readJson(String path)throws IOException{
        File file = new File(path);
        //使用文件流读取JSON
        try(Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)){
            int ch;
            StringBuilder sb = new StringBuilder();
            //从文件流中读取
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            String jsonStr = sb.toString();
            return new JSONObject(jsonStr);
        }
    }
}
