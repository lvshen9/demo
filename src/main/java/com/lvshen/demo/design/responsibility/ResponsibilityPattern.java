package com.lvshen.demo.design.responsibility;

import org.junit.Test;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-10-13 16:09
 * @since JDK 1.8
 */
public class ResponsibilityPattern {
    @Test
    public void test() {
        //组装责任链
        Handler handler1 = new ConcreteHandler1();
        Handler handler2 = new ConcreteHandler2();
        Handler handler3 = new ConcreteHandler3();
        handler1.setNext(handler2);

        //提交请求
        handler1.handleRequest("two");
    }
}
