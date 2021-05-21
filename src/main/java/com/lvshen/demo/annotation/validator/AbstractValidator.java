package com.lvshen.demo.annotation.validator;

/**
 * Description: 校验器规范
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2021-5-20 16:37
 * @since JDK 1.8
 */
public interface AbstractValidator {
    void check(Object o);
}
