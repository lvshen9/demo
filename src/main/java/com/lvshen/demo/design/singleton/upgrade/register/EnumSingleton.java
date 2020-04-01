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

    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public  EnumSingleton getInstance() {
        return INSTANCE;
    }



}
