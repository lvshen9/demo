package com.lvshen.demo.event;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Description:支付服务
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-8-28 14:02
 * @since JDK 1.8
 */
@Component
public class PayService {

    @Autowired
    public ApplicationEventPublisher applicationEventPublisher;

    /**
     * 支付成功后，发布事件
     * @param orderId
     */
    public void paySuccess(String orderId) {
        if (StringUtils.isNotBlank(orderId)) {
            applicationEventPublisher.publishEvent(new PayEvent(this, orderId));
        }
    }

    public void paySuccessOld(String orderId) {
        if (StringUtils.isNotBlank(orderId)) {
            //1.修改订单状态
            //2.发送短信通知用户
            //3.通知仓库发货

            //其他，如果需求需要微信通知，QQ通知，下发优惠券，发放积分...,写道这里多么臃肿。
        }
    }
}
