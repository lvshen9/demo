package com.lvshen.demo.design.strategy;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-10-16 17:37
 * @since JDK 1.8
 */
public class Context {
    public How2WorkStrategy getHow2WorkStrategy() {
        return how2WorkStrategy;
    }

    public void setHow2WorkStrategy(How2WorkStrategy how2WorkStrategy) {
        this.how2WorkStrategy = how2WorkStrategy;
    }

    private How2WorkStrategy how2WorkStrategy;

    public String how2Work() {
        return how2WorkStrategy.how2WorkFun();
    }

}
