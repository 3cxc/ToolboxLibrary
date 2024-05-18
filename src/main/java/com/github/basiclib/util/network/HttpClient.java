package com.github.basiclib.util.network;

import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 网络处理部分,用以从服务端下载/获取内容
 * @since Plugin Basic Lib 1.0
 * @author 3cxc
 * @version 1.1
 */
final public class HttpClient {

    /**
     * 从远程URL获取内容
     * @param url 远程URL
     * @return 获取到的内容(如果不为null)
     * @throws IOException 如果发生IO错误
     */
    public static @Nullable String HttpContent(String url)throws IOException {
        //使用流从URL获取内容
        StringBuilder buffer = new StringBuilder();
        URL u = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) u.openConnection();
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))){
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                buffer.append(line);
            }
        }
        //返回
        if (!buffer.toString().equals(" ") & !buffer.toString().isEmpty()){
            return buffer.toString();
        }
        return null;
    }
}