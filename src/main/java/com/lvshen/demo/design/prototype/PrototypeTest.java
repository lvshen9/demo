package com.lvshen.demo.design.prototype;

import org.junit.Test;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/12/30 13:47
 * @since JDK 1.8
 */
public class PrototypeTest {
    @Test
    public void test() throws CloneNotSupportedException {
        Realizetype obj1=new Realizetype();
        Realizetype obj2=(Realizetype)obj1.clone();
        System.out.println("obj1==obj2?"+(obj1==obj2));
    }
}
