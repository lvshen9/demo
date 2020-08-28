package com.lvshen.demo.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Description:仓库服务
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-8-28 14:21
 * @since JDK 1.8
 */
@Component
public class WarehouseService {
    @EventListener
    public void sendProduct(PayEvent payEvent) {
        String orderId = payEvent.getOrderId();
        //发货功能
        System.out.println(String.format("支付成功，准备发货，订单【%s】",orderId));
    }
}
