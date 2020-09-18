package com.lvshen.demo.design.template;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-9-18 11:47
 * @since JDK 1.8
 */
public class ConcreteClass extends AbstractClass {
    @Override
    public void abstractMethod1() {
        System.out.println("抽象方法1的实现被调用...");
    }

    @Override
    public void abstractMethod2() {
        System.out.println("抽象方法2的实现被调用...");
    }
}
