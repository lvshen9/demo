package com.lvshen.demo.design.mediator;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/10/8 13:59
 * @since JDK 1.8
 */
public abstract class Colleague {
    protected Mediator mediator;

    public void setMedium(Mediator mediator) {
        this.mediator = mediator;
    }

    public abstract void receive();

    public abstract void send();
}
