package com.lvshen.demo.design.factory;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/12/30 17:10
 * @since JDK 1.8
 */
public class ConcreteFactory2 implements Factory {
    @Override
    public Product product() {
        System.out.println("具体工厂2生成-->具体产品2...");
        return new ConcreteProduct2();
    }
}
