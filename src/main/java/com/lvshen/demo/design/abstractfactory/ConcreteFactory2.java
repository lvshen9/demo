package com.lvshen.demo.design.abstractfactory;

/**
 * Description: 苹果工厂
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-9-17 15:13
 * @since JDK 1.8
 */
public class ConcreteFactory2 implements AbstractFactory {
    public Product1 newProduct1()
    {
        System.out.println("具体工厂 2 【苹果工厂】生成-->具体产品 12...");
        return new ConcreteProduct12();
    }
    public Product2 newProduct2()
    {
        System.out.println("具体工厂 2 【苹果工厂】生成-->具体产品 22...");
        return new ConcreteProduct22();
    }
}
