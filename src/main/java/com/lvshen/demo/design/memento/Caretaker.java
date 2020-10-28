package com.lvshen.demo.design.memento;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-10-28 16:49
 * @since JDK 1.8
 */
public class Caretaker {
    private Memento memento;

    public void setMemento(Memento m) {
        memento = m;
    }

    public Memento getMemento() {
        return memento;
    }
}
