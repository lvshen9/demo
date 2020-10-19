package com.lvshen.demo.design.strategy;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-10-16 17:24
 * @since JDK 1.8
 */
public class BusStrategy implements How2WorkStrategy{
    @Override
    public String how2WorkFun() {
        return "公交";
    }
}
