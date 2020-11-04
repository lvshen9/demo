package com.lvshen.demo.design.expression;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-11-3 17:09
 * @since JDK 1.8
 */
public class ExpressionTest {
    @Test
    public void test() {
        Context context = new Context();
        List<AbstractExpression> list = new ArrayList<>();

        list.add(new TerminalExpression());
        list.add(new NonterminalExpression());
        list.add(new TerminalExpression());
        list.add(new TerminalExpression());

        for (AbstractExpression abstractExpression : list) {
            abstractExpression.interpret("context");
        }
    }
}
