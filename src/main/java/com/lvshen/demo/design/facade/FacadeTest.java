package com.lvshen.demo.design.facade;

/**
 * Description: 外观模式
 *
 * @author yuange
 * @version 1.0
 * @date: 2020/1/20 14:21
 * @since JDK 1.8
 */
public class FacadeTest {
    public static void main(String[] args){
        Facade facade = new Facade();
        facade.method();
    }
}
