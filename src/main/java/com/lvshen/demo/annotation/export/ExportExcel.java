package com.lvshen.demo.annotation.export;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description: 导出Excel
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-8-31 15:02
 * @since JDK 1.8
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExportExcel {
    /**
     *  需要导出的实体class
     */
    Class<?> beanClass();
}
