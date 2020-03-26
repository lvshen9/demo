package com.lvshen.demo.redis.subscribe;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/3/24 10:16
 * @since JDK 1.8
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GoodsMessage extends RedisMessage {
    private String goodsType;
    private String number;
}
