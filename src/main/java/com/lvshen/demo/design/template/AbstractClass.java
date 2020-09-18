package com.lvshen.demo.design.template;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-9-18 11:45
 * @since JDK 1.8
 */
public abstract class AbstractClass {
    //模板方法
    public void templateMethod() {
        specificMethod();
        abstractMethod1();
        abstractMethod2();
    }
    //具体方法
    public void specificMethod() {
        System.out.println("抽象类中的具体方法被调用...");
    }
    public abstract void abstractMethod1(); //抽象方法1
    public abstract void abstractMethod2(); //抽象方法2
}
