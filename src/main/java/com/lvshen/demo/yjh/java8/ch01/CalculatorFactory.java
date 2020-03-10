package com.lvshen.demo.yjh.java8.ch01;

public abstract class CalculatorFactory {

    public static Calculator getInstance() {
        return new BasicCalculator();
    }
}
