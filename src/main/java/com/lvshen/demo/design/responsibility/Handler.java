package com.lvshen.demo.design.responsibility;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-10-13 16:04
 * @since JDK 1.8
 */
public abstract class Handler {
    private Handler next;

    public void setNext(Handler next) {
        this.next = next;
    }

    public Handler getNext() {
        return next;
    }

    //处理请求的方法
    public abstract void handleRequest(String request);
}
