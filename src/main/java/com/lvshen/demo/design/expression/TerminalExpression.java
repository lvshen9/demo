package com.lvshen.demo.design.expression;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-11-3 16:41
 * @since JDK 1.8
 */
public class TerminalExpression implements AbstractExpression{
    @Override
    public Object interpret(String info) {
        System.out.println("进入终结符表达式的处理逻辑");
        return null;
    }
}
