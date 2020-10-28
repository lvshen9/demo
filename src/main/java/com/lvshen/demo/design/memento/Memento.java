package com.lvshen.demo.design.memento;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-10-28 16:48
 * @since JDK 1.8
 */
public class Memento {
    private String state;

    public Memento(String state) {
        this.state = state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
