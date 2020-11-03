package com.lvshen.demo.design.state;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-11-2 11:36
 * @since JDK 1.8
 */
public class Context {
    private State state;

    //定义环境类的初始状态
    public Context() {
        this.state = new ConcreteStateA();
    }

    //设置新状态
    public void setState(State state) {
        this.state = state;
    }

    //读取状态
    public State getState() {
        return (state);
    }

    //对请求做处理
    public void handle() {
        state.handle(this);
    }
}
