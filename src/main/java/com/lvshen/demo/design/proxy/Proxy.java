package com.lvshen.demo.design.proxy;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2020/1/19 16:49
 * @since JDK 1.8
 */
public class Proxy implements Subject {

    private RealSubject realSubject;

    @Override
    public void request() {
        if (realSubject == null) {
            realSubject = new RealSubject();
        }
        preRequest();
        realSubject.request();
        postRequest();
    }

    public void preRequest() {
        System.out.println("访问真实主题之前的预处理!!!");
    }

    public void postRequest() {
        System.out.println("访问真实主题之后的后续处理!!!");
    }
}
