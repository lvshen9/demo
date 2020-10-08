package com.lvshen.demo.annotation.log;

import java.lang.annotation.*;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/10/8 10:54
 * @since JDK 1.8
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationLog {
    String module() default ""; // 操作模块
    String type() default "";  // 操作类型
    String desc() default "";  // 操作说明
}
