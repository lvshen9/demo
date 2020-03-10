package com.lvshen.demo.design.flyweight;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2020/1/20 14:30
 * @since JDK 1.8
 */

public class UnsharedConcreteFlyweight {
    private String info;

    public UnsharedConcreteFlyweight(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
