package com.lvshen.demo.design.command;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/10/11 18:38
 * @since JDK 1.8
 */

public abstract class Receiver {
    //抽象接收者，定义每个接收者都必须完成的业务
    public abstract void doSomething();
}

