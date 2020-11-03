package com.lvshen.demo.design.state;

import org.junit.Test;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-11-2 11:39
 * @since JDK 1.8
 */
public class StateTest {
    @Test
    public void test() {
        Context context=new Context();    //创建环境
        context.handle();    //处理请求
        context.handle();
        context.handle();
        context.handle();
    }
}
