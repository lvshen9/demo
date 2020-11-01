package com.lvshen.demo.design.visitor;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/10/31 11:56
 * @since JDK 1.8
 */
public interface Visitor {
    void visit(ConcreteElementA element);
    void visit(ConcreteElementB element);
}
