package com.lvshen.demo.design.command;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/10/11 18:43
 * @since JDK 1.8
 */
public class ConcreteCommand1 extends Command {
    //对哪个Receiver类进行命令处理
    private Receiver receiver;

    //构造函数传递接收者
    public ConcreteCommand1(Receiver _receiver) {
        this.receiver = _receiver;
    }

    //必须实现一个命令
    @Override
    public void execute() {
        //业务处理
        this.receiver.doSomething();
    }
}
