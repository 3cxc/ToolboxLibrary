package com.github.basiclib.Logger;

import org.apache.logging.log4j.LogManager;

public class LoggerBase implements Logger {

    /**
     * 内部日志记录器
     */
    static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(org.apache.logging.log4j.Logger.class);

    //包装API
    @Override
    public void info(Object message){
        LoggerBase.logger.info(message);
    }

    @Override
    public void warn(Object message){
        LoggerBase.logger.warn(message);
    }

    @Override
    public void warn(Object message,Throwable t){
        LoggerBase.logger.warn(message,t);
    }

    @Override
    public void error(Object message){
        LoggerBase.logger.error(message);
    }
    @Override
    public void error(Object message,Throwable t){
        LoggerBase.logger.error(message,t);
    }

    @Override
    public void error(Throwable t){
        LoggerBase.logger.error(t);
    }

    @Override
    public void error(String message, Object p0, Object p1) {
        LoggerBase.logger.error(message,p0,p1);
    }
}
