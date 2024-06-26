package com.github.tlib.Configuration;

import com.github.tlib.Logger.Logger;
import com.github.tlib.Logger.LoggerBase;
import com.github.tlib.util.JsonWriter;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 一个基本的JSON配置文件管理器
 * @since ToolboxLibrary 1.0
 * @author 3cxc
 * @version 3.1
 */
public class JsonConfig implements ConfigInterface,JsonConfigInterface{

    volatile private JSONObject jsonObject;
    final private String configPath;
    final private Logger logger = new LoggerBase();

    /**
     * 初始化一个新的配置文件管理器
     * @param path 配置文件路径
     */
    public JsonConfig(String path){
        configPath = path;
        reloadConfig();
    }

    /**
     * 初始化一个新的配置文件管理器
     * <p>
     * 并使用提供的JSON内容作为配置文件内容
     * <p>
     * 使用该构造方法会导致配置文件路径设置为null
     * @param object 配置文件内容
     */
    public JsonConfig(JSONObject object){
        configPath = null;
        jsonObject = object;
    }


    /**
     * 使用一个JSON对象覆盖配置文件原内容
     * @param json 新的配置文件内容
     */
    @Override
    public void setConfig(JSONObject json){
        jsonObject = json;
    }

    /**
     * 使用提供的HashMap对象
     * <p>
     * 来覆盖配置文件原内容
     * @param map 新的配置文件内容
     */
    @Override
    public void setConfig(HashMap<String,Object> map){
        jsonObject = new JSONObject(map);
    }

    /**
     * 使用提供的Map对象
     * <p>
     * 来覆盖配置文件原内容
     * @param map 新的配置文件内容
     */
    @Override
    public void setConfig(Map<String,Object> map){
        jsonObject = new JSONObject(map);
    }

    @Override
    public String getPath(){
        return configPath;
    }

    @Override
    public void reloadConfig(){
        if (configPath != null){
            //缓存内容
            JSONObject tmp = jsonObject;
            try {
                if (exists()){
                    jsonObject = JsonWriter.readJson(configPath);
                }
            }catch (IOException e){//发生IO异常则回滚
                jsonObject = tmp;
                logger.error(e.getMessage(),e);
            }
        }
    }


    /**
     * 获取配置文件中key对应的String值
     * @param key 键
     * @param defaultValue 默认值
     * @return 返回获取到的值，如果获取失败则返回默认值
     */
    @Override
    final public String getString(String key, String defaultValue) {
        try {
            return jsonObject.getString(key);
        }catch (Exception t){
            logger.error(t);
            return defaultValue;
        }
    }

    /**
     * 获取配置文件中key对应的Int值
     * @param key 键
     * @param defaultValue 默认值
     * @return 返回获取到的值，如果获取失败则返回默认值
     */
    @Override
    final public int getInt(String key, int defaultValue) {
        try {
            return jsonObject.getInt(key);
        }catch (Exception t){
            logger.error(t);
            return defaultValue;
        }
    }

    @Override
    final public @Nullable String getString(String key){
        return jsonObject.getString(key);
    }

    @Override
    final public int getInt(String key){
        return jsonObject.getInt(key);
    }

    @Override
    final public boolean getBoolean(String key){
        return jsonObject.getBoolean(key);
    }

    @Override
    final public @Nullable List<String> getStringList(String key){
        if (jsonObject != null){
            JSONArray array = jsonObject.getJSONArray(key);
            List<String> list = new ArrayList<>();
            //把array的内容逐行写入list
            for (int i = 0 ; i < array.length() ; i++){
                list.add(array.getString(i));
            }
            return list;
        }
        return null;
    }

    @Override
    final public @Nullable List<Integer> getIntList(String key){
        if (jsonObject != null){
            JSONArray array = jsonObject.getJSONArray(key);
            List<Integer> list = new ArrayList<>();
            //把array的内容逐行写入list
            for (int i = 0 ; i < array.length() ; i++){
                list.add(array.getInt(i));
            }
            return list;
        }
        return null;
    }

    @Override
    final public @Nullable List<Double> getDoubleList(String key){
        if (jsonObject != null){
            JSONArray array = jsonObject.getJSONArray(key);
            List<Double> list = new ArrayList<>();
            //把array的内容逐行写入list
            for (int i = 0 ; i < array.length() ; i++){
                list.add(array.getDouble(i));
            }
            return list;
        }
        return null;
    }

    @Override
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void saveConfig(){
        if (configPath != null & jsonObject != null){
            File config = new File(configPath);
            File oldConfig = new File(configPath+".old");
            File newConfig = new File(configPath+".loc");
            try{
                if (oldConfig.exists()) oldConfig.delete();
                config.renameTo(oldConfig);
                //如果存在了临时配置文件，先删除，避免影响后续写入操作(不存在就创建)
                if (!newConfig.exists()){
                    newConfig.createNewFile();
                }
                //将内存中配置文件写入新配置文件
                JsonWriter.writeJson(newConfig.getPath(),jsonObject.toMap());
                //重命名新配置文件为(正常的)配置文件
                newConfig.renameTo(config);
                oldConfig.delete();
            }catch (IOException e) {
                logger.error(e.getMessage(),e);
                //清除临时文件
                newConfig.delete();
                //回退配置文件
                oldConfig.renameTo(config);
            }
        }
    }

    @Override
    public boolean exists(){
        return configPath != null && new File(configPath).exists();
    }

    @Override
    public void put(String key,Object value){
        if (jsonObject != null){
            jsonObject.put(key, value);
        }
    }

    @Override
    public void put(String key, boolean value){
        if (jsonObject != null){
            jsonObject.put(key, value);
        }
    }

    @Override
    public void put(String key, double value){
        if (jsonObject != null){
            jsonObject.put(key, value);
        }
    }

    @Override
    public void put(String key, float value){
        if (jsonObject != null){
            jsonObject.put(key, value);
        }
    }

    @Override
    public void put(String key, int value) {
        if (jsonObject != null){
            jsonObject.put(key, value);
        }
    }

    @Override
    public void put(String key, long value) {
        if (jsonObject != null){
            jsonObject.put(key, value);
        }
    }

    @Override
    public void put(String key, Map<?, ?> value) {
        if (jsonObject != null){
            jsonObject.put(key, value);
        }
    }

    @Override
    public void remove(String key){
        if (jsonObject != null){
            jsonObject.remove(key);
        }
    }
}
