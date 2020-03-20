package com.lvshen.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.Redisson;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/12/26 14:52
 * @since JDK 1.8
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisSpringTest {

    public static final String GET_NEXT_CODE = "get_next_code_spring";
    public static final String HASH_KEY = "hash_key";

    private static final String VALUE_KEY = "value_key";
    // private static final String HASH_KEY = "hash_key";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testCode() {
        long code = getCode(GET_NEXT_CODE);
        log.info("code:{}",code);
    }

    @Test
    public void setStringTest() {
        stringRedisTemplate.opsForValue().set(VALUE_KEY,"I am Lvshen9");
        System.out.println("Test is OK");
    }

    @Test
    public void setHashTest() {
        stringRedisTemplate.opsForHash().put(HASH_KEY,"lvshen","a value that is lvshen");
        System.out.println("Test is OK");
    }


    /**
     * spring原生获取
     * @param key
     * @return
     */
    public long getCode(String key) {
        RedissonClient redissonClient = getRedissonClient();

        RAtomicLong atomicVar = redissonClient.getAtomicLong(key);
        if (!atomicVar.isExists()) {
            atomicVar.set(100);
        }
        long value = atomicVar.incrementAndGet(); // 多线程调用该方法，不会造成数据丢失
        return value;
    }

    public void saveHash(String key) {
        RedissonClient redissonClient = getRedissonClient();

    }

    private RedissonClient getRedissonClient() {
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress("redis://192.168.42.128:6379");
        singleServerConfig.setPassword("lvshen");
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }
}
