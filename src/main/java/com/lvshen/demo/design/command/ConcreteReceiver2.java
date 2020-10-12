package com.lvshen.demo.design.command;

import cn.hutool.core.lang.Console;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/10/11 18:40
 * @since JDK 1.8
 */
public class ConcreteReceiver2 extends Receiver {
    @Override
    public void doSomething() {
        Console.log("执行ConcreteReceiver2的方法...");
    }
}
