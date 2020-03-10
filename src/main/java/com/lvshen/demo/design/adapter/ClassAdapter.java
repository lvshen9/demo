package com.lvshen.demo.design.adapter;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2020/1/20 10:00
 * @since JDK 1.8
 */
public class ClassAdapter extends Adaptee implements Target {
    @Override
    public void request() {
        super.specificRequest();
    }
}
