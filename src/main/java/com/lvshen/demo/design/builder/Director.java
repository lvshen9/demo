package com.lvshen.demo.design.builder;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/12/31 9:37
 * @since JDK 1.8
 */
public class Director {
    private Builder builder;

    public Director(Builder builder) {
        this.builder = builder;
    }

    public Product construct(){
        builder.buildPartA();
        builder.buildPartB();
        builder.buildPartC();
        return builder.getResult();
    }
}
