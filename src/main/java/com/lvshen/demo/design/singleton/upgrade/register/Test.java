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

        Thread t1 = new Thread(() -> {

            EnumSingleton.INSTANCE.setData(new Member());
            Member member = (Member) EnumSingleton.INSTANCE.getData();
            System.out.println(member);
        });
        Thread t2 = new Thread(() -> {

            EnumSingleton.INSTANCE.setData(new Member());
            Member member = (Member) EnumSingleton.INSTANCE.getData();
            System.out.println(member);
        });

        t1.start();
        t2.start();

    }

    @org.junit.Test
    public void test2(){
        EnumSingleton instance1 = EnumSingleton.INSTANCE.getInstance();
        EnumSingleton instance2 = EnumSingleton.INSTANCE.getInstance();
        System.out.println(instance1 = instance2);
    }
}
