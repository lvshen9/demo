package com.lvshen.demo.annotation.easyexport;

import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.*;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-12-20 13:54
 * @since JDK 1.8
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EasyExcelExport {
    /**
     * 当数量超过asyncSize时，系统转为异步导出的方式
     * 默认 1000
     * @return
     */
    int asyncSize() default 1000;

    /**
     * 导出的最大数量
     * 默认10000
     * @return
     */
    int maxSize() default 10000;

    /**
     * 异步导出文件保存时间，默认1小时
     * @return
     */
    int saveTime() default 1;

    String excelPoolBeanName() default StringUtils.EMPTY;
}
