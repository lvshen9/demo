package com.lvshen.demo.redis.subscribe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/3/24 10:17
 * @since JDK 1.8
 */
@Service
public class Publisher {

    @Autowired
    private RedisTemplate redisTemplate;

    public void pushMessage(String topic, RedisMessage message) {
        redisTemplate.convertAndSend(topic,message);
    }
}
