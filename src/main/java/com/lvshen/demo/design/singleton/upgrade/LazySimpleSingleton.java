package com.lvshen.demo.design.singleton.upgrade;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/4/1 19:06
 * @since JDK 1.8
 */
public class LazySimpleSingleton {
    private static volatile LazySimpleSingleton instance = null;

    private LazySimpleSingleton(){
    }

    public static LazySimpleSingleton getInstance() {
        if (instance == null) {
            synchronized (LazySimpleSingleton.class) {
                if (instance == null) {
                    instance = new LazySimpleSingleton();
                }
            }
        }
        return instance;
    }
}
