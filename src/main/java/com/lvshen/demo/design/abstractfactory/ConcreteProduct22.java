package com.lvshen.demo.design.abstractfactory;

import com.lvshen.demo.design.factory.Product;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/12/30 17:06
 * @since JDK 1.8
 */
public class ConcreteProduct22 implements Product2 {
    @Override
    public void show() {
        System.out.println("具体产品22显示【苹果-音响】...");
    }
}
