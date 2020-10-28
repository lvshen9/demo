package com.lvshen.demo.design.memento;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-10-28 16:48
 * @since JDK 1.8
 */
public class Originator {
    private String state;

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public Memento createMemento() {
        return new Memento(state);
    }

    public void restoreMemento(Memento m) {
        this.setState(m.getState());
    }
}
