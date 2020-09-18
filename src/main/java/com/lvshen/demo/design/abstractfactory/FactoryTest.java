package com.lvshen.demo.design.abstractfactory;


import com.lvshen.demo.design.factory.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/12/30 17:11
 * @since JDK 1.8
 */
@Slf4j
public class FactoryTest {
    @Test
    public void test() {
        ConcreteFactory2 concreteFactory = new ConcreteFactory2();
        Product2 product = concreteFactory.newProduct2();
        product.show();
    }
}
