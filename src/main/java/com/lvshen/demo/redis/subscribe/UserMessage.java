package com.lvshen.demo.redis.subscribe;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/3/24 10:17
 * @since JDK 1.8
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserMessage extends RedisMessage {
    private String userId;
    private String username;
    private String password;
}
