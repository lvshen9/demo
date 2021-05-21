package com.lvshen.demo.annotation.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2021-5-20 16:36
 * @since JDK 1.8
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidatorHandler {
    /**
     *  校验的类
     */
    Class<?> validators();
}
