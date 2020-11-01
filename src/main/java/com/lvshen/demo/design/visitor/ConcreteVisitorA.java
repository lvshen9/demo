package com.lvshen.demo.design.visitor;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/10/31 11:57
 * @since JDK 1.8
 */
public class ConcreteVisitorA implements Visitor {
    @Override
    public void visit(ConcreteElementA element) {
        System.out.println("具体访问者A访问-->" + element.operationA());
    }

    @Override
    public void visit(ConcreteElementB element) {
        System.out.println("具体访问者A访问-->" + element.operationB());
    }
}
