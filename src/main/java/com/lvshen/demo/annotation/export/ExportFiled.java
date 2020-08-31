package com.lvshen.demo.annotation.export;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description: 导出字段注解
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-8-31 15:04
 * @since JDK 1.8
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExportFiled {
    /**
     * 导出字段顺序
     */
    String number();

    /**
     * 导出字段名称
     */
    String name();
}
