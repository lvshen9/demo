package com.lvshen.demo.design.template;

import org.junit.Test;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-9-18 11:44
 * @since JDK 1.8
 */
public class TemplateMethodPattern {
    @Test
    public void test() {
        AbstractClass tm = new ConcreteClass();
        tm.templateMethod();
    }
}
