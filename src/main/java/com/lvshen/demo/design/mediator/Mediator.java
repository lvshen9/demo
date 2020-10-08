package com.lvshen.demo.design.mediator;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/10/8 13:58
 * @since JDK 1.8
 */
public abstract class Mediator {
    public abstract void register(Colleague colleague);
    public abstract void relay(Colleague cl); //转发
}
