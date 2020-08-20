package com.lvshen.demo.design.singleton.upgrade.register;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/4/1 19:18
 * @since JDK 1.8
 */
public enum  EnumSingleton {
    INSTANCE;
    public  EnumSingleton getInstance() {
        return INSTANCE;
    }



}
