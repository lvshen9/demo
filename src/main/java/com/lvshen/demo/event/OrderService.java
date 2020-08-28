package com.lvshen.demo.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Description:订单服务
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-8-28 14:10
 * @since JDK 1.8
 */
@Component
public class OrderService {

    @EventListener
    public void updateOrderStatus(PayEvent payEvent) {
        String orderId = payEvent.getOrderId();
        //修改订单状态
        System.out.println(String.format("支付成功，修改订单【%s】状态为已支付！！！",orderId));
    }
}
