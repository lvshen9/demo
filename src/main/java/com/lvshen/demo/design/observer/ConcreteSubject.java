package com.lvshen.demo.design.observer;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-10-23 9:30
 * @since JDK 1.8
 */
public class ConcreteSubject extends Subject {
    @Override
    public void notifyObserver() {
        System.out.println("具体目标发生改变...");
        System.out.println("--------------");

        for (Object obs : observers) {
            ((Observer) obs).response();
        }
    }
}
