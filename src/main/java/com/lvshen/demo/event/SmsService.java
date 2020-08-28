package com.lvshen.demo.event;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Description:短信服务
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-8-28 14:16
 * @since JDK 1.8
 */
@Component
public class SmsService {

    @EventListener
    public void sendMessage(PayEvent payEvent) {
        String orderId = payEvent.getOrderId();
        //短信功能
        System.out.println(String.format("支付成功，发送【%s】短信",orderId));
    }
}
