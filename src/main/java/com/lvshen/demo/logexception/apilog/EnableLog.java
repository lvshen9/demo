package com.lvshen.demo.logexception.apilog;

import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.*;

/**
 * @author 接口日志
 * 注意，开启日志记录注解的方法，只能有一个入参，对于多个入参可做适当封装
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableLog {

    OperationType type();

    /**
     * 接口提供方
     *
     * @return
     */
    String provider();

    String currentSystem() default StringUtils.EMPTY;

    /**
     * 类型 IN-本系统对外api or OUT-调的外部接口
     *
     * @return
     */
    ModelType model();

    /**
     * 成功标志Str
     *
     * @return
     */
    String successStr() default StringUtils.EMPTY;

    /**
     * 允许重试次数，默认-1，即可无限重试
     *
     * @return
     */
    long allowRetry() default -1;

    /**
     * 是否自动重试 0-否
     *
     * @return
     */
    String needAuto() default "0";

    /**
     * 方法描述
     *
     * @return
     */
    String desc() default StringUtils.EMPTY;
}
