package com.panpan.springdesign.creationdesign.singletondesign;

/**
 * @Description
 * @Author xupan
 * @Date2020/12/2 9:55
 * @Version V1.0
 **/
public class SingletonClass {
    private static volatile SingletonClass instance = null;
    //    private static SingletonClass instance = null;
    //    这里注意如果没有volatile没有可见性保证。
    private SingletonClass() {}
    public static SingletonClass getInstance() {
        if (instance == null) {
        synchronized(SingletonClass.class){
            if (instance == null) {
            instance = new SingletonClass();
        }
        }
    }
    return instance;
    }
}
