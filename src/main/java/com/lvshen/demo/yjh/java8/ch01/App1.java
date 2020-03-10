package com.lvshen.demo.yjh.java8.ch01;

public class App1 implements A {

    /**
     * 接口的方法一样可重写
     */
    @Override
    public void doSth() {
        System.out.println("inside App1");
    }

    public static void main(String[] args) {
        new App1().doSth();
    }
}


/**
 * 接口可以使用default关键字，方法体中可以写类容
 */
interface A {
    default void doSth() {
        System.out.println("inside A");
    }
}
