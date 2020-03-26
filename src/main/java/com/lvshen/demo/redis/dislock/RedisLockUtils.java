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

/**
 * redis实现分布式事务锁
 *
 * @author lvshen
 * @date 2019-08-11
 */
@Slf4j
@Component
public class RedisLockUtils {

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 拿锁的脚本
     */
    private static final String LUA_SCRIPT_LOCK = "return redis.call('SET', KEYS[1], ARGV[1], 'NX', 'PX', ARGV[2]) ";

    /**
     * 拿锁的脚本
     */
    private static RedisScript<String> scriptLock = new DefaultRedisScript<>(LUA_SCRIPT_LOCK, String.class);

    /**
     * 释放锁锁的脚本
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
                // 参数分别对应了脚本、key序列化工具、value序列化工具，后面参数对应scriptLock字符串中的三个变量值，
                // KEYS[1]，ARGV[1]，ARGV[2]，含义为锁的key，key对应的value，以及key 的存在时间(单位毫秒)
                String result = (String) redisTemplate.execute(scriptLock,
                        redisTemplate.getStringSerializer(),
                        redisTemplate.getStringSerializer(),
                        Collections.singletonList(key),
                        uuid.toString(),
                        String.valueOf(timeOut));
                if (result != null && result.equals("OK")) {
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
        // 当返回0的时候可能因为超时而锁已经过期
        if (new Integer("1").equals(execute)) {
            log.info("释放锁");
        }
    }

}
