package com.lvshen.demo.redis.subscribe;

import lombok.Data;

import java.io.Serializable;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/3/24 10:15
 * @since JDK 1.8
 */
@Data
public class RedisMessage implements Serializable {
    public String msgId;
    public long createStamp;
}
