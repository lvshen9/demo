package com.lvshen.demo.design.bridge;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2020/1/20 10:16
 * @since JDK 1.8
 */
public class ConcreteImplementorA implements Implementor {
    @Override
    public void operationImpl() {
        System.out.println("具体实现化(Concrete Implementor)角色被访问");
    }
}
