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
    /**
     * 业务类型，如增删改查
     *
     * @return
     */
    OperationType opType();

    /**
     * 业务对象名称，如订单、库存、价格
     *
     * @return
     */
    String opBusinessName();

    /**
     * 业务唯一标识id，例如订单id
     *
     * @return
     */
    String opBusinessId();
}
