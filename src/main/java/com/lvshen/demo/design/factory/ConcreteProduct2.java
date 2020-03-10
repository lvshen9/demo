package com.lvshen.demo.design.factory;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/12/30 17:07
 * @since JDK 1.8
 */
public class ConcreteProduct2 implements Product {
    @Override
    public void show() {
        System.out.println("具体产品2显示...");
    }
}
