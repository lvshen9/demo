package com.lvshen.demo.design.decorator;

/**
 * Description:装饰者模式
 *
 * @author yuange
 * @version 1.0
 * @date: 2020/1/20 13:52
 * @since JDK 1.8
 */
public class DecoratorTest {
    public static void main(String[] args){
        Component p = new ConcreteComponent();
        p.operation();
        System.out.println("------------------");
        Component concreteDecorator = new ConcreteDecorator(p);
        concreteDecorator.operation();
    }
}
