package com.lvshen.demo.design.responsibility;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-10-13 16:05
 * @since JDK 1.8
 */
public class ConcreteHandler3 extends Handler {
    @Override
    public void handleRequest(String request) {
        if (request.equals("three")) {
            System.out.println("具体处理者3负责处理该请求！");
        } else {
            if (getNext() != null) {
                getNext().handleRequest(request);
            } else {
                System.out.println("没有人处理该请求！");
            }
        }
    }
}
