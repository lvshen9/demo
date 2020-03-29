package com.lvshen.demo.cas;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/3/29 11:24
 * @since JDK 1.8
 */
public class Singleton {
    private Singleton() {
    }

    private static AtomicReference<Singleton> singletonAtomicReference = new AtomicReference<>();

    public static Singleton getInstance() {
        while (true) {
            Singleton singleton = singletonAtomicReference.get();// 获得singleton
            if (singleton != null) {// 如果singleton不为空，就返回singleton
                return singleton;
            }
            // 如果singleton为空，创建一个singleton
            singleton = new Singleton();
            // CAS操作，预期值是NULL，新值是singleton
            // 如果成功，返回singleton
            // 如果失败，进入第二次循环，singletonAtomicReference.get()就不会为空了
            if (singletonAtomicReference.compareAndSet(null, singleton)) {
                return singleton;
            }
        }
    }
}
