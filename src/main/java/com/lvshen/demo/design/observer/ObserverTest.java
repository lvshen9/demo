package com.lvshen.demo.design.observer;

import org.junit.Test;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-10-23 9:39
 * @since JDK 1.8
 */
public class ObserverTest {
    @Test
    public void test() {
        Subject subject=new ConcreteSubject();
        Observer obs1=new ConcreteObserver1();
        Observer obs2=new ConcreteObserver2();
        subject.add(obs1);
        subject.add(obs2);
        subject.notifyObserver();
    }
}
