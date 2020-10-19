package com.lvshen.demo.design.strategy;

import cn.hutool.core.lang.Console;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-10-16 17:38
 * @since JDK 1.8
 */
public class TestStrategy {
    @org.junit.Test
    public void test() {
        Context context = new Context();
        How2WorkStrategy how2WorkStrategy = new CarStrategy();
        context.setHow2WorkStrategy(how2WorkStrategy);
        String s = context.how2Work();
        Console.log(s);
        Console.log("---------------------");
        how2WorkStrategy = new BusStrategy();
        context.setHow2WorkStrategy(how2WorkStrategy);
        String s1 = context.how2Work();
        Console.log(s1);


    }
}
