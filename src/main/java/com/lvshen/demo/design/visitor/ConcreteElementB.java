package com.lvshen.demo.design.visitor;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/10/31 11:59
 * @since JDK 1.8
 */
public class ConcreteElementB implements Element {
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public String operationB() {
        return "具体元素B的操作。";
    }
}
