package com.lvshen.demo.design.proxy.staticproxy;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-8-26 16:07
 * @since JDK 1.8
 */
public class Phone implements IPhone{
    public void call() {
        System.out.println("打电话");
    }
}
