package com.lvshen.demo.design.strategy;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-10-16 17:24
 * @since JDK 1.8
 */
public class SubwayStrategy implements How2WorkStrategy{
    @Override
    public String how2WorkFun() {
        return "地铁";
    }
}
