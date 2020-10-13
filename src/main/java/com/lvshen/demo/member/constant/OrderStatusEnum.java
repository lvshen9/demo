package com.lvshen.demo.member.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-10-1 18:48
 * @since JDK 1.8
 */
@Getter
@AllArgsConstructor
public enum OrderStatusEnum {
    WAIT_PAY("待付款"),
    PAID("已付款未发货"),
    DELIVERY("已发货"),
    RECEIVE("已收货");

    private String desc;

}
