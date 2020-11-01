package com.lvshen.demo.design.visitor;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/10/31 11:58
 * @since JDK 1.8
 */
public interface Element {
    void accept(Visitor visitor);
}
