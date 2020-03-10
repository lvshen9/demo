package com.lvshen.demo.design.bridge;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2020/1/20 10:17
 * @since JDK 1.8
 */
public abstract class Abstraction {
    protected Implementor implementor;

    public Abstraction(Implementor implementor) {
        this.implementor = implementor;
    }
    public abstract void operation();
}
