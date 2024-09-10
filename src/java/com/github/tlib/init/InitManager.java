package com.github.tlib.init;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;
import org.jetbrains.annotations.NotNull;

/**
 * 一个简单的任务调度器
 * <p>
 * 可以让多个程序使用统一的任务调度流程
 * @since ToolboxLibrary 1.1
 * @author 3cxc
 * @version 1.2
 */
@SuppressWarnings("unused")
public class InitManager {

    final private ClassToInstanceMap<Initable> initableClassToLoadInstanceMap = MutableClassToInstanceMap.create();
    final private ClassToInstanceMap<Initable> initableClassToRunInstanceMap = MutableClassToInstanceMap.create();
    final private ClassToInstanceMap<Initable> initableClassToStopInstanceMap = MutableClassToInstanceMap.create();

    /**
     * 向Map存储一个需要在加载阶段调用的类(Map是顺序遍历的)
     * @param key 继承{@link Initable}的类
     * @param value 继承{@link Initable}的类的实例，应该与key一致
     * @since ToolboxLibrary 1.1
     * @author 3cxc
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void putLoadMethodToMap(Class key, Initable value){
        initableClassToLoadInstanceMap.put(key,value);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public void putRunMethodToMap(Class key, Initable value){
        initableClassToRunInstanceMap.put(key,value);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public void putStopMethodToMap(Class key, Initable value){
        initableClassToStopInstanceMap.put(key,value);
    }

    public void load(){
        executeMethod(this.initableClassToLoadInstanceMap);
    }
    public void run(){
        executeMethod(this.initableClassToRunInstanceMap);
    }
    public void stop(){
        executeMethod(this.initableClassToStopInstanceMap);
    }

    private void executeMethod(final @NotNull ClassToInstanceMap<Initable> map){
        for (Initable initable : map.values()){
            initable.run();
        }
    }
}
