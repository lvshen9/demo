package com.lvshen.demo.design.abstractfactory;

/**
 * Description: 小米工厂
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-9-17 15:13
 * @since JDK 1.8
 */
public class ConcreteFactory1 implements AbstractFactory {
    public Product1 newProduct1() {
        System.out.println("具体工厂 1 【小米工厂】 生成-->具体产品 11...");
        return new ConcreteProduct11();
    }
    public Product2 newProduct2() {
        System.out.println("具体工厂 1 【小米工厂】 生成-->具体产品 21...");
        return new ConcreteProduct21();
    }
}
