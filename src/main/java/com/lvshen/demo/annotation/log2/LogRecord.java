package com.lvshen.demo.annotation.log2;

import com.lvshen.demo.authenticatedstreams.service.ModuleTypeEnum;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2024/3/31 19:08
 * @since JDK 1.8
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogRecord {
    /**
     * 操作类型
     * @return
     */
    OperateTypeEnum type();

    /**
     * 接口提供方
     *
     * @return
     */
    String provider() default StringUtils.EMPTY;

    /**
     * 业务模块
     * @return
     */
    ModuleTypeEnum module() default ModuleTypeEnum.SRM;

    /**
     * 操作的业务单号 如#param.code
     * @return
     */
    String busCode() default StringUtils.EMPTY;

    /**
     * 业务模块描述
     * 描述修饰的这个方法是做什么用的
     * @return
     */
    String remark() default StringUtils.EMPTY;

    /**
     * operate_log:操作记录
     */
    String operateLog() default StringUtils.EMPTY;

    /**
     * param_before:操作前入参
     * 这里一般是json形式
     */
    String paramBefore() default StringUtils.EMPTY;
}
