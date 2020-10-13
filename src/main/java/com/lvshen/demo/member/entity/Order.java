package com.lvshen.demo.member.entity;

import com.lvshen.demo.member.constant.OrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description: 订单id
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/9/22 20:40
 * @since JDK 1.8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    /**
     * 订单id
     */
    private String id;

    /**
     * 下单用户id
     */
    private String memberId;

    /**
     * 订单状态
     */
    private String status;

    /**
     * 行为：构建一个发货状态的行为
     * @return
     */
    public void buildDeliveryStatus() {
        this.status = OrderStatusEnum.DELIVERY.name();
    }

}
