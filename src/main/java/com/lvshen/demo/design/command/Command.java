package com.lvshen.demo.design.command;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/10/11 18:42
 * @since JDK 1.8
 */
public abstract class Command {
    //每个命令类都必须有一个执行命令的方法
    public abstract void execute();
}
