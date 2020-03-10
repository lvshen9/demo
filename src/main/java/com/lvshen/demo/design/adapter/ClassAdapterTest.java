package com.lvshen.demo.design.adapter;

/**
 * Description:适配器模式
 *
 * @author yuange
 * @version 1.0
 * @date: 2020/1/20 10:03
 * @since JDK 1.8
 */
public class ClassAdapterTest {
    public static void main(String[] args){
        System.out.println("类适配器模式测试：");
        Target target = new ClassAdapter();
        target.request();
    }
}
