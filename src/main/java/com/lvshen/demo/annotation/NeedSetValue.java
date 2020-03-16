package com.lvshen.demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/3/16 14:09
 * @since JDK 1.8
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NeedSetValue {
    Class<?> beanClass();
    String param();
    String method();
    String targetFiled();

}
