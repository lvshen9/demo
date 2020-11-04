package com.lvshen.demo.design.expression;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-11-3 16:40
 * @since JDK 1.8
 */
public interface AbstractExpression {
    Object interpret(String info);    //解释方法
}
