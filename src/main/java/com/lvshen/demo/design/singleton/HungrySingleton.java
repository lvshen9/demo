package com.lvshen.demo.design.singleton;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/12/30 10:06
 * @since JDK 1.8
 */
public class HungrySingleton {
    private static final HungrySingleton instance = new HungrySingleton();
    private HungrySingleton(){}
    public static HungrySingleton getInstance(){
        return instance;
    }
}
