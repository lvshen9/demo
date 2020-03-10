package com.lvshen.demo.design.factory;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/12/30 17:06
 * @since JDK 1.8
 */
public class ConcreteProduct1 implements Product {
    @Override
    public void show() {
        System.out.println("具体产品1显示...");
    }
}
