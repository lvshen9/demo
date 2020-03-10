package com.lvshen.demo.design.adapter;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2020/1/20 9:59
 * @since JDK 1.8
 */
public class Adaptee {
    public void specificRequest() {
        System.out.println("适配者中的业务代码被调用！");
    }
}
