package com.lvshen.demo.design.proxy.staticproxy;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-8-26 16:09
 * @since JDK 1.8
 */
public class Test {
    public static void main(String[] args) {
        PhoneProxy proxy = new PhoneProxy();
        proxy.call();
    }
}
