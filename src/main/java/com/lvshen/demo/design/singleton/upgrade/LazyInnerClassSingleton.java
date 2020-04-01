package com.lvshen.demo.design.singleton.upgrade;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/4/1 19:12
 * @since JDK 1.8
 */
public class LazyInnerClassSingleton {
    private LazyInnerClassSingleton() {
        if (LazyHolder.INSTANCE != null) {
            throw new RuntimeException("该构造方法禁止获取");
        }
    }

    public static final LazyInnerClassSingleton getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final LazyInnerClassSingleton INSTANCE = new LazyInnerClassSingleton();
    }
}
