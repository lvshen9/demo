package com.lvshen.demo.design.composite;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/5/5 18:43
 * @since JDK 1.8
 */
public class Leaf implements Component {
    private String name;

    public Leaf(String name) {
        this.name = name;
    }

    @Override
    public void add(Component c) {

    }

    @Override
    public void remove(Component c) {

    }

    @Override
    public Component getChild(int i) {
        return null;
    }

    @Override
    public void operation() {
        System.out.println("树叶" + name + ": 被访问！！");
    }
}
