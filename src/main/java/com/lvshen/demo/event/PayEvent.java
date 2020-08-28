package com.lvshen.demo.event;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

/**
 * Description: 支付事件
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-8-28 13:56
 * @since JDK 1.8
 */
public class PayEvent extends ApplicationEvent {

    //订单id
    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public PayEvent(Object source,String orderId) {
        super(source);
        this.orderId = orderId;
    }


}
