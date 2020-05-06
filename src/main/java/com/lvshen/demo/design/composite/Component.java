package com.lvshen.demo.design.composite;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/5/5 18:41
 * @since JDK 1.8
 */
public interface Component {
    public void add(Component c);
    public void remove(Component c);
    public Component getChild(int i);
    public void operation();
}
