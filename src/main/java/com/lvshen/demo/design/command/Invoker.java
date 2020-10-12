package com.lvshen.demo.design.command;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/10/11 18:45
 * @since JDK 1.8
 */
public class Invoker {
    private Command command;

    //接受命令
    public void setCommand(Command _command) {
        this.command = _command;
    }

    //执行命令
    public void action() {
        this.command.execute();
    }
}
