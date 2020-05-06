package com.lvshen.demo.design.composite;

import java.util.ArrayList;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/5/5 18:44
 * @since JDK 1.8
 */
public class Composite implements Component {

    private ArrayList<Component> children = new ArrayList<Component>();
    @Override
    public void add(Component c) {
        children.add(c);
    }

    @Override
    public void remove(Component c) {
        children.remove(c);
    }

    @Override
    public Component getChild(int i) {
        return children.get(i);
    }

    @Override
    public void operation() {
        for (Component child : children) {
            child.operation();
        }
    }
}
