package com.lvshen.demo.design.flyweight;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2020/1/20 14:57
 * @since JDK 1.8
 */
public interface Flyweight {
    void operation(UnsharedConcreteFlyweight state);
}
