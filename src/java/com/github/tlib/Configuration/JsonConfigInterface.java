package com.github.tlib.Configuration;

import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * json配置文件接口
 * <p>
 * 包含JSON配置文件专用的操作方法
 * @since ToolboxLibrary 1.0
 * @author 3cxc
 * @version 1.1
 */
public interface JsonConfigInterface {
    void setConfig(JSONObject json);
    void setConfig(HashMap<String,Object> map);
    void setConfig(Map<String,Object> map);
    String getString(String key, String defaultValue);
    @Nullable String getString(String key);
    int getInt(String key, int defaultValue);
    int getInt(String key);
    boolean getBoolean(String key);
    @Nullable List<String> getStringList(String key);
    @Nullable List<Integer> getIntList(String key);
    @Nullable List<Double> getDoubleList(String key);
    void put(String key,Object value);
    void put(String key, boolean value);
    void put(String key, double value);
    void put(String key, float value);
    void put(String key, int value);
    void put(String key, long value);
    void put(String key, Map<?, ?> value);
    void remove(String key);
}
