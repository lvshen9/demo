package com.lvshen.demo.design.observer;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-10-23 9:31
 * @since JDK 1.8
 */
public class ConcreteObserver2 implements Observer {
    @Override
    public void response() {
        System.out.println("具体观察者2作出反应！");
    }
}
