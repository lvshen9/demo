package com.lvshen.demo.design.singleton.upgrade.register;

import com.lvshen.demo.member.entity.Member;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/4/1 19:21
 * @since JDK 1.8
 */
public class Test {
    @org.junit.Test
    public void test1() {

    }

    @org.junit.Test
    public void test2(){
        EnumSingleton instance1 = EnumSingleton.INSTANCE.getInstance();
        EnumSingleton instance2 = EnumSingleton.INSTANCE.getInstance();
        System.out.println(instance1 == instance2);
    }

}
