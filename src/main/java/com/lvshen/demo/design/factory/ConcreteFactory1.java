package com.lvshen.demo.design.factory;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/12/30 17:09
 * @since JDK 1.8
 */
public class ConcreteFactory1 implements Factory {
    @Override
    public Product product() {
        System.out.println("具体工厂1生成-->具体产品1...");
        return new ConcreteProduct1();
    }
}
