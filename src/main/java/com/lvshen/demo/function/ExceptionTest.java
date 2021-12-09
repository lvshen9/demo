package com.lvshen.demo.function;

import cn.hutool.core.lang.Console;
import org.junit.Test;

import javax.validation.Validator;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2021-11-29 10:53
 * @since JDK 1.8
 */
public class ExceptionTest {

    @Test
    public void testIsTrue() {
        ValidateUtils.isTrue(true).throwMessage("抛出异常");
    }

    @Test
    public void testIsTrueOrFalse() {
        ValidateUtils.isTrueOrFalse(false).trueOrFalseHandle(() -> Console.log("true：我觉得很不错"), () -> Console.log("false：你很有问题"));
    }

    @Test
    public void testIsBlankOrNoBlank() {
        ValidateUtils.isBlankOrNoBlank("Lvshen").presentOrElseHandle(System.out::println, () -> {
            System.out.println("非空字符串");
        });
    }
}
