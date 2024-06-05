package com.github.basiclib.Configuration;

import com.github.basiclib.Logger.Logger;
import com.github.basiclib.Logger.LoggerBase;
import com.github.basiclib.util.JsonWriter;
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
 * @since Plugin Basic Lib 1.0
 * @author 3cxc
 * @version 3.0
 */
public class JsonConfig implements ConfigInterface,JsonConfigInterface{

    //存储JSON内容
    volatile private JSONObject jsonObject;
    //配置文件路径
    final private String configPath;
    //日志API
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
    final public void setConfig(JSONObject json){
        jsonObject = json;
    }

    /**
     * 使用提供的HashMap对象
     * <p>
     * 来覆盖配置文件原内容
     * @param map 新的配置文件内容
     */
    @Override
    final public void setConfig(HashMap<String,Object> map){
        jsonObject = new JSONObject(map);
    }

    /**
     * 使用提供的Map对象
     * <p>
     * 来覆盖配置文件原内容
     * @param map 新的配置文件内容
     */
    @Override
    final public void setConfig(Map<String,Object> map){
        jsonObject = new JSONObject(map);
    }

    /**
     * 获取配置文件路径
     * @return 返回配置文件的路径
     */
    @Override
    final public String getPath(){
        return configPath;
    }

    /**
     * 重载配置文件,这将会抛弃内存中的配置文件
     * <p>
     * 注意：如果无法找到文件或出现异常，则继续使用内存中的配置文件
     */
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
            if (jsonObject.has(key)) {
                return jsonObject.getString(key);
            } else {
                return defaultValue;
            }
        }catch (Exception t){
            logger.error(t);
            return defaultValue;
        }
    }

    /**
     * 获取配置文件中key对应的String值
     * @param key 键
     * @return 返回获取到的值，如果获取失败则返回null
     */
    @Override
    final public @Nullable String getString(String key){
        try {
            return jsonObject.getString(key);
        }catch (Throwable t){
            logger.error(t);
            return null;
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
            if (jsonObject.has(key)) {
                return jsonObject.getInt(key);
            } else {
                return defaultValue;
            }
        }catch (Throwable t){
            logger.error(t);
            //防止发生什么意外错误没有默认返回
            return defaultValue;
        }
    }

    /**
     * 获取配置文件中key对应的Int值
     * @param key 键
     * @return 返回获取到的值，如果获取失败则返回0
     */
    @Override
    final public int getInt(String key){
        try {
            return jsonObject.getInt(key);
        }catch (Throwable t){
            logger.error(t);
            return 0;
        }
    }

    /**
     * 获取配置文件中key对应的boolean值
     * @param key 键
     * @param defaultValue 默认值
     * @return 返回获取到的值，如果获取失败则返回默认值
     */
    @Override
    final public boolean getBoolean(String key, Boolean defaultValue) {
        try {
            if (jsonObject.has(key)) {
                return jsonObject.getBoolean(key);
            } else {
                return defaultValue;
            }
        }catch (Throwable t){
            logger.error(t);
            //防止发生什么意外错误没有默认返回
            return defaultValue;
        }
    }

    /**
     * 获取配置文件中key对应的boolean值
     * @param key 键
     * @return 返回获取到的值，如果获取失败则返回false
     */
    @Override
    final public boolean getBoolean(String key){
        try {
            return jsonObject.getBoolean(key);
        }catch (Throwable t){
            logger.error(t);
            return false;
        }
    }

    /**
     * 获取配置文件中key对应的String List
     * @param key 键
     * @return 返回获取到的值，如果获取失败则返回null
     */
    @Override
    final public @Nullable List<String> getStringList(String key){
        if (jsonObject != null){
            try {
                JSONArray array = jsonObject.getJSONArray(key);
                if (!array.isEmpty()){
                    List<String> list = new ArrayList<>();
                    //把array的内容写入list
                    for (int i = 0 ; i < array.length() ; i++){
                        list.add(array.getString(i));
                    }
                    //返回list
                    return list;
                }else {
                    return null;
                }
            }catch (Throwable t){//如果发生错误
                logger.error(t.getMessage(),t);
                return null;
            }
        }else {return null;}
    }

    /**
     * 获取配置文件中key对应的Int List
     * @param key 键
     * @return 返回获取到的值，如果获取失败则返回null
     */
    @Override
    final public @Nullable List<Integer> getIntList(String key){
        if (jsonObject != null){
            try {
                JSONArray array = jsonObject.getJSONArray(key);
                if (!array.isEmpty()){
                    List<Integer> list = new ArrayList<>();
                    //把array的内容写入list
                    for (int i = 0 ; i < array.length() ; i++){
                        list.add(array.getInt(i));
                    }
                    //返回list
                    return list;
                }else {
                    return null;
                }
            }catch (Throwable t){//如果发生错误
                logger.error(t.getMessage(),t);
                return null;
            }
        }else {return null;}
    }

    /**
     * 获取配置文件中key对应的Double List
     * @param key 键
     * @return 返回获取到的值，如果获取失败则返回null
     */
    @Override
    final public @Nullable List<Double> getDoubleList(String key){
        if (jsonObject != null){
            try {
                JSONArray array = jsonObject.getJSONArray(key);
                if (!array.isEmpty()){
                    List<Double> list = new ArrayList<>();
                    //把array的内容写入list
                    for (int i = 0 ; i < array.length() ; i++){
                        list.add(array.getDouble(i));
                    }
                    //返回list
                    return list;
                }else {
                    return null;
                }
            }catch (Throwable t){//如果发生错误
                logger.error(t.getMessage(),t);
                return null;
            }
        }else {return null;}
    }

    /**
     * 保存配置文件
     */
    @Override
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void saveConfig(){
        if (configPath != null & jsonObject != null){
            File config = new File(configPath);
            File oldConfig = new File(configPath+".old");
            File newConfig = new File(configPath+".loc");
            try{
                //先检测有没有更旧的配置文件.如果有先删除
                if (oldConfig.exists()){
                    oldConfig.delete();
                }
                //原配置文件重命名成旧配置文件
                config.renameTo(oldConfig);
                //如果存在了临时配置文件，先删除，避免影响后续写入操作(不存在就创建)
                if (!newConfig.exists()){
                    newConfig.createNewFile();
                }
                //将内存中配置文件写入新配置文件
                JsonWriter.writeJson(newConfig.getPath(),jsonObject.toMap());
                //重命名新配置文件为(正常的)配置文件
                newConfig.renameTo(config);
                //删除旧配置文件
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

    /**
     * 检查配置文件是否已创建
     * @return 为true表示文件已存在，为false表示文件不存在或路径为null
     */
    @Override
    final public boolean exists(){
        if (configPath != null){
            return new File(configPath).exists();
        }
        return false;
    }

    /**
     * 添加一个Object对象到配置文件中
     * @param key 键
     * @param value 对应的值
     */
    @Override
    final public void put(String key,Object value){
        if (jsonObject != null){
            jsonObject.put(key, value);
        }
    }

    /**
     * 添加一个boolean到配置文件中
     * @param key 键
     * @param value 对应的值
     */
    @Override
    final public void put(String key, boolean value){
        if (jsonObject != null){
            jsonObject.put(key, value);
        }
    }

    /**
     * 添加一个double到配置文件中
     * @param key 键
     * @param value 对应的值
     */
    @Override
    final public void put(String key, double value){
        if (jsonObject != null){
            jsonObject.put(key, value);
        }
    }

    /**
     * 添加一个float到配置文件中
     * @param key 键
     * @param value 对应的值
     */
    @Override
    final public void put(String key, float value){
        if (jsonObject != null){
            jsonObject.put(key, value);
        }
    }

    /**
     * 添加一个int到配置文件中
     * @param key 键
     * @param value 对应的值
     */
    @Override
    final public void put(String key, int value) {
        if (jsonObject != null){
            jsonObject.put(key, value);
        }
    }

    /**
     * 添加一个long到配置文件中
     * @param key 键
     * @param value 对应的值
     */
    @Override
    final public void put(String key, long value) {
        if (jsonObject != null){
            jsonObject.put(key, value);
        }
    }

    /**
     * 添加一个Map到配置文件中
     * @param key 键
     * @param value 对应的值
     */
    @Override
    final public void put(String key, Map<?, ?> value) {
        if (jsonObject != null){
            jsonObject.put(key, value);
        }
    }


    /**
     * 从配置文件中删除一个键及其对应的值
     * @param key 键
     */
    @Override
    final public void remove(String key){
        if (jsonObject != null){
            jsonObject.remove(key);
        }
    }
}
