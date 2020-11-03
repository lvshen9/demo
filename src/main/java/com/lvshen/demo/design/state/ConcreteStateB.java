package com.lvshen.demo.design.state;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-11-2 11:37
 * @since JDK 1.8
 */
public class ConcreteStateB extends State{
    public void handle(Context context)
    {
        System.out.println("当前状态是 B.");
        context.setState(new ConcreteStateA());
    }
}
