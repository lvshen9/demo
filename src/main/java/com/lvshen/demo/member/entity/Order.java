package com.lvshen.demo.member.entity;

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

}
