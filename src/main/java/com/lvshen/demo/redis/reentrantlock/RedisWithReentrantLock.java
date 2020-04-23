package com.lvshen.demo.redis.reentrantlock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/4/22 19:01
 * @since JDK 1.8
 */
@Component
public class RedisWithReentrantLock {

    @Autowired
    private RedisTemplate redisTemplate;


    private static final String REDIS_VALUE = "r_lock";


    private ThreadLocal<Map<String, Integer>> lockers = new ThreadLocal<>();

    private boolean _lock(String key) {
        return redisTemplate.opsForValue().setIfAbsent(key, REDIS_VALUE, 20, TimeUnit.SECONDS);
    }

    private void _unlock(String key) {
        redisTemplate.delete(key);
    }

    private Map<String, Integer> currentLockers() {
        Map<String, Integer> refs = lockers.get();
        if (refs != null) {
            return refs;
        }

        lockers.set(new HashMap<>());
        return lockers.get();
    }

    public boolean lock(String key) {
        Map<String, Integer> refs = currentLockers();
        Integer refCnt = refs.get(key);
        if (refCnt != null) {
            refs.put(key, refCnt + 1);
            return true;
        }
        boolean ok = this._lock(key);
        if (!ok) {
            return false;
        }

        refs.put(key, 1);
        return true;
    }

    public boolean unlock(String key) {
        Map<String, Integer> refs = currentLockers();
        Integer refCnt = refs.get(key);
        if (refCnt == null) {
            return false;
        }

        refCnt = refCnt - 1;
        if ((refCnt > 0)) {
            refs.put(key, refCnt);
        } else {
            refs.remove(key);
            this._unlock(key);
        }
        return true;
    }


}
