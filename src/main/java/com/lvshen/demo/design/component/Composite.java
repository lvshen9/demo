package com.lvshen.demo.design.component;

import java.util.ArrayList;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-10-22 9:49
 * @since JDK 1.8
 */
public class Composite extends Component {
    private ArrayList<Component> children = new ArrayList<>();

    public void add(Component c) {
        children.add(c);
    }

    public void remove(Component c) {
        children.remove(c);
    }

    public Component getChild(int i) {
        return children.get(i);
    }

    public void operation() {
        for (Object obj : children) {
            ((Component) obj).operation();
        }
    }
}
