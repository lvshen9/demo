package com.lvshen.demo.design.component;

import org.junit.Test;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-10-22 9:51
 * @since JDK 1.8
 */
public class ComponentTest {
    @Test
    public void test() {
        Component c0 = new Composite();
        Component c1 = new Composite();
        Component leaf1 = new Leaf("Lvshen1");
        Component leaf2 = new Leaf("Lvshen2");
        Component leaf3 = new Leaf("Lvshen3");
        c0.add(leaf1);
        c0.add(c1);
        c1.add(leaf2);
        c1.add(leaf3);
        c0.operation();
    }
}
