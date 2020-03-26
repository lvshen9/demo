package com.lvshen.demo.redis.dislock;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * redis实现分布式事务锁
 *
 * @author lvshen
 * @date 2019-08-11
 */
@Slf4j
@Component
public class RedisTemplateLockUtils {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 释放锁的脚本
     */
    public static final String LUA_SCRIPT_UN_LOCK = "if " +
                                                "(redis.call('GET', KEYS[1]) == ARGV[1] )" +
                                          "then " +
                                                "return redis.call('DEL', KEYS[1]) " +
                                          "else " +
                                                "return 0 " +
                                          "end";

    /**
     * 释放锁的脚本
     */
    private static RedisScript<Long> scriptLock2 = new DefaultRedisScript<>(LUA_SCRIPT_UN_LOCK, Long.class);

    public static final String LOCK_PRE = "lock.";

    private String getKey(String key) {
        return LOCK_PRE + key;
    }

    /***
     * 加锁
     * @param key
     * @param timeOut
     * @return
     */
    public RedisLock getLock(String key,long timeOut,long tryLockTimeout) {
        long timestamp = System.currentTimeMillis();
        try {
            key = getKey(key);
            UUID uuid = UUID.randomUUID();
            while ((System.currentTimeMillis() - timestamp) < tryLockTimeout) {
                Boolean aBoolean =
                        redisTemplate.opsForValue().setIfAbsent(key, uuid.toString(), timeOut, TimeUnit.MILLISECONDS);
                if (aBoolean) {
                    return new RedisLock(key, uuid.toString());
                } else {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(JSON.toJSONString(e.getStackTrace()));
        }
        return null;
    }

    /**
     * 释放锁
     * @param lock
     */
    public void releaseLock(RedisLock lock) {
        Object execute = redisTemplate.execute(scriptLock2,
                redisTemplate.getStringSerializer(),
                redisTemplate.getStringSerializer(),
                Collections.singletonList(lock.getKey()),
                lock.getValue()
        );
        if (new Integer("1").equals(execute)) {
            log.info("释放锁");
        }
    }

}
