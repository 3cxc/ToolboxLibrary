package com.github.tlib.Logger;

/**
 * 通用日志接口
 */
public interface Logger {
    void info(Object message);
    void warn(Object message);
    void warn(Object message,Throwable t);
    void error(Object message);
    void error(Object message,Throwable t);
    void error(Throwable t);
    void error(String message,Object p0,Object p1);
    void debug(Object message);
}
