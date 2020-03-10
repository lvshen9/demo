package com.lvshen.demo.design.factory;

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
        ConcreteFactory1 concreteFactory1 = new ConcreteFactory1();
        Product product = concreteFactory1.product();
        product.show();
    }
}
