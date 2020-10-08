package com.lvshen.demo.design.mediator;

import cn.hutool.core.lang.Console;
import org.junit.Test;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/10/8 14:02
 * @since JDK 1.8
 */
public class MediatorPattern {
    @Test
    public void testMediator() {
        Mediator md = new ConcreteMediator();
        Colleague c1, c2;
        c1 = new ConcreteColleague1();
        c2 = new ConcreteColleague2();
        md.register(c1);
        md.register(c2);
        c1.send();
        Console.log("------------");
        c2.send();
    }
}
