package com.lvshen.demo.java8.ch01;

public class App2 implements B, D {
    public static void main(String[] args) {
        new App2().doSth();
    }
}

interface B {
    default void doSth() {
        System.out.println("inside B");
    }
}

interface D extends B {
    default void doSth() {
        System.out.println("inside D");
    }
}





