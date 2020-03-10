package com.lvshen.demo.design.decorator;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2020/1/20 13:49
 * @since JDK 1.8
 */
public class Decorator implements Component {
    private Component component;

    public Decorator(Component component) {
        this.component = component;
    }

    @Override
    public void operation() {
        component.operation();
    }
}
