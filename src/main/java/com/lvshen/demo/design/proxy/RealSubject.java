package com.lvshen.demo.design.proxy;

/**
 * Description:真实类
 *
 * @author yuange
 * @version 1.0
 * @date: 2020/1/19 16:48
 * @since JDK 1.8
 */
public class RealSubject implements Subject {
    @Override
    public void request() {
        System.out.println("真实类访问方法！！！！");
    }
}
