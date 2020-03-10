package com.lvshen.demo.design.singleton;

/**
 * Description:懒汉式单列
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/12/30 10:01
 * @since JDK 1.8
 */
public class LazySingleton {
    private static volatile LazySingleton instance = null;

    private LazySingleton(){}

    public static synchronized LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }
}
