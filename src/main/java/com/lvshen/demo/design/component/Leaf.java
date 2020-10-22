package com.lvshen.demo.design.component;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-10-22 9:48
 * @since JDK 1.8
 */
public class Leaf extends Component {

    private String name;

    public Leaf(String name) {
        this.name = name;
    }

    public void add(Component c) {
    }

    public void remove(Component c) {
    }

    public Component getChild(int i) {
        return null;
    }

    public void operation() {
        System.out.println("树叶" + name + "：被访问！");
    }
}
