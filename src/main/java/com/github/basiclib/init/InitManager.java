package com.github.basiclib.init;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;

/**
 * 一个简单的 加载/运行/结束 任务调度器
 * <p>
 * 可以让多个程序使用统一的任务调度流程
 * @since Plugin Basic Lib 1.1
 * @author 3cxc
 * @version 1.0
 */
@SuppressWarnings("unused")
final public class InitManager {
    /**
     * 存储需要在加载阶段进行调用的类的Map
     * @since Plugin Basic Lib 1.1
     */
    final private ClassToInstanceMap<Initable> initableClassToInstanceMapLoad = MutableClassToInstanceMap.create();
    /**
     * 存储需要在运行阶段进行调用的类的Map
     * @since Plugin Basic Lib 1.1
     */
    final private ClassToInstanceMap<Initable> initableClassToInstanceMapRun = MutableClassToInstanceMap.create();
    /**
     * 存储需要在停止阶段进行调用的类的Map
     * @since Plugin Basic Lib 1.1
     */
    final private ClassToInstanceMap<Initable> initableClassToInstanceMapStop = MutableClassToInstanceMap.create();

    /**
     * 向Map存储一个需要在加载阶段调用的类
     * <p>
     * 注意:Map是顺序遍历，即顺序调用类
     * @param key 继承{@link Initable}的类
     * @param value 继承{@link Initable}的类的实例化，应该与key一致
     * @since Plugin Basic Lib 1.1
     * @author 3cxc
     */
    public void putLoadMap(Class<Initable> key, Initable value){
        initableClassToInstanceMapLoad.put(key,value);
    }

    /**
     * 向Map存储一个需要在运行阶段调用的类
     * <p>
     * 注意:Map是顺序遍历，即顺序调用类
     * @param key 继承{@link Initable}的类
     * @param value 继承{@link Initable}的类的实例化，应该与key一致
     * @since Plugin Basic Lib 1.1
     * @author 3cxc
     */
    public void putRunMap(Class<Initable> key, Initable value){
        initableClassToInstanceMapRun.put(key,value);
    }

    /**
     * 向Map存储一个需要在停止阶段调用的类
     * <p>
     * 注意:Map是顺序遍历，即顺序调用类
     * @param key 继承{@link Initable}的类
     * @param value 继承{@link Initable}的类的实例化，应该与key一致
     * @since Plugin Basic Lib 1.1
     * @author 3cxc
     */
    public void putStopMap(Class<Initable> key, Initable value){
        initableClassToInstanceMapStop.put(key,value);
    }

    /**
     * 顺序遍历的执行Map中的每个类
     * <p>
     * 仅在程序的加载阶段调用
     * @since Plugin Basic Lib 1.1
     * @author 3cxc
     */
    public void load(){
        for (Initable initable : initableClassToInstanceMapLoad.values()){
            initable.run();
        }
    }


    /**
     * 顺序遍历的执行Map中的每个类
     * <p>
     * 仅在程序的运行阶段调用
     * @since Plugin Basic Lib 1.1
     * @author 3cxc
     */
    public void run(){
        for (Initable initable : this.initableClassToInstanceMapRun.values()){
            initable.run();
        }
    }

    /**
     * 顺序遍历的执行Map中的每个类
     * <p>
     * 仅在程序的停止阶段调用
     * @since Plugin Basic Lib 1.1
     * @author 3cxc
     */
    public void stop(){
        for (Initable initable : this.initableClassToInstanceMapStop.values()){
            initable.run();
        }
    }
}
