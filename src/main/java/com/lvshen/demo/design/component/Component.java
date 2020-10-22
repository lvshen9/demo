package com.lvshen.demo.design.component;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-10-22 9:46
 * @since JDK 1.8
 */
public abstract class Component {
    public abstract void add(Component c);
    public abstract void remove(Component c);
    public abstract Component getChild(int i);
    public abstract void operation();
}
