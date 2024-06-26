package com.github.tlib.Configuration;

/**
 * 通用的配置文件接口
 * <p>
 * 以确保适配多个不同的配置文件管理器
 * @since ToolboxLibrary 1.0
 * @author 3cxc
 * @version 1.1
 */
public interface ConfigInterface {
    String getPath();
    void reloadConfig();
    void saveConfig();
    boolean exists();
}
