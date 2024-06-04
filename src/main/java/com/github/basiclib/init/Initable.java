package com.github.basiclib.init;

/**
 * 任务调度器的统一注册接口
 * <p>
 * 所有要注册到任务调度器的任务都必须继承此接口
 * <p>
 * 并实现run()方法
 * @since Plugin Basic Lib 1.1
 * @author 3cxc
 * @version 1.0
 */
public interface Initable {

    /**
     * 包含一个任务需要执行的内容的方法
     * @since Plugin Basic Lib 1.1
     * @author 3cxc
     */
    void run();
}
