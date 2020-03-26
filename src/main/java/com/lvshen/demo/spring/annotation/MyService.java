package com.lvshen.demo.spring.annotation;

import java.lang.annotation.*;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/3/26 17:51
 * @since JDK 1.8
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyService {
    String value() default "";
}
