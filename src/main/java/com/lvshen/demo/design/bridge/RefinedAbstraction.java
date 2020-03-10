package com.lvshen.demo.design.bridge;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2020/1/20 10:19
 * @since JDK 1.8
 */
public class RefinedAbstraction extends Abstraction {
    public RefinedAbstraction(Implementor implementor) {
        super(implementor);
    }

    @Override
    public void operation() {
        System.out.println("扩展抽象化(Refined Abstraction)角色被访问" );
        implementor.operationImpl();
    }
}
