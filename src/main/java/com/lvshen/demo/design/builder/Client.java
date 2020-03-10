package com.lvshen.demo.design.builder;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/12/31 9:39
 * @since JDK 1.8
 */
public class Client {
    public static void main(String[] args){
        Builder builder = new ConcreteBuilder();
        Director director = new Director(builder);
        Product construct = director.construct();
        construct.show();
    }
}
