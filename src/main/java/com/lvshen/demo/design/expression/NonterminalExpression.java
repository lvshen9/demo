package com.lvshen.demo.design.expression;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-11-3 16:44
 * @since JDK 1.8
 */
public class NonterminalExpression implements AbstractExpression {
    private AbstractExpression exp1;
    private AbstractExpression exp2;
    @Override
    public Object interpret(String info) {
        System.out.println("进入非终结表达式的逻辑");
        return null;
    }
}
