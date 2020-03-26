package com.lvshen.demo.redis.dislock;

import lombok.Data;

/**
 * @author lvshen
 * @date 2019-08-11
 */
@Data
public class RedisLock {

    /**
     * 锁的key
     */
    private String key;
    /**
     * 锁的值
     */
    private String value;

    public RedisLock(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
