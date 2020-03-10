package com.lvshen.demo.design.facade;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2020/1/20 14:17
 * @since JDK 1.8
 */
public class Facade {
    private SubSystem01 obj1 = new SubSystem01();
    private SubSystem02 obj2 = new SubSystem02();
    private SubSystem03 obj3 = new SubSystem03();

    public void method() {
        obj1.method1();
        obj2.method2();
        obj3.method3();
    }

}
