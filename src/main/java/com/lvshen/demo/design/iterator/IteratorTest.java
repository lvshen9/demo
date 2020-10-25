package com.lvshen.demo.design.iterator;

import org.junit.Test;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/10/21 21:41
 * @since JDK 1.8
 */
public class IteratorTest {
    @Test
    public void test() {
        Aggregate ag = new ConcreteAggregate();
        ag.add("微信号：Lvshen_9");
        ag.add("头条号：Lvshen的技术小屋");
        ag.add("CSDN：Lvshen的技术小屋");
        System.out.print("聚合的内容有：");
        Iterator it = ag.getIterator();
        while (it.hasNext()) {
            Object ob = it.next();
            System.out.print(ob.toString() + "\t");
        }
        Object ob = it.first();
        System.out.println("\nFirst：" + ob.toString());
    }
}
