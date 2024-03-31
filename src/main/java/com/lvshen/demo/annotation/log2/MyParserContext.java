package com.lvshen.demo.annotation.log2;

import org.springframework.expression.ParserContext;

import javax.validation.constraints.NotNull;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2024/3/31 19:14
 * @since JDK 1.8
 */
public class MyParserContext implements ParserContext {
    @Override
    public boolean isTemplate() {
        return true;
    }

    @NotNull
    @Override
    public String getExpressionPrefix() {
        return "{";
    }

    @NotNull
    @Override
    public String getExpressionSuffix() {
        return "}";
    }
}
