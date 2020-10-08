package com.lvshen.demo.design.mediator;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/10/8 14:01
 * @since JDK 1.8
 */
public class ConcreteMediator extends Mediator {
    private List<Colleague> colleagues = Lists.newArrayList();

    @Override
    public void register(Colleague colleague) {
        if (!colleagues.contains(colleague)) {
            colleagues.add(colleague);
            colleague.setMedium(this);
        }
    }

    @Override
    public void relay(Colleague cl) {
        for (Colleague ob : colleagues) {
            if (!ob.equals(cl)) {
                ob.receive();
            }
        }
    }
}
