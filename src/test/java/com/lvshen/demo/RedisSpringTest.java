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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;


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

    //创建限流令牌
    private static Semaphore semaphore = new Semaphore(50);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    //模拟布隆过滤器
    @Test
    public void testBit() {
        String userId = "10001001001";
        int hasValue = Math.abs(userId.hashCode()); //key 做hash运算
        long index = (long) (hasValue % Math.pow(2, 32)); //hash值与数组长度取模
        Boolean bloomFilter = redisTemplate.opsForValue().setBit("user_bloom_filter", index, true);
        log.info("user_bloom_filter:{}",bloomFilter);
    }

    //缓存击穿解决方法
    @Test
    public void testUnEffactiveCache() {
        String userId = "1001";
        //1.系统初始化是init 布隆过滤器，将所有数据存于redis bitmap中
        //2.查询是先做判断，该key是否存在与redis中
        int hasValue = Math.abs(userId.hashCode()); //key 做hash运算
        long index = (long) (hasValue % Math.pow(2, 32)); //hash值与数组长度取模
        Boolean result = redisTemplate.opsForValue().getBit("user_bloom_filter", index);
        if (!result) {
            log.info("该userId在数据库中不存在：{}",userId);
            //return null;
        }
        //3.从缓存中获取
        //4.缓存中没有，从数据库中获取，并存放于redis中
    }

    @Test
    public void testCacheAvalanche() throws InterruptedException {
        //1.从redis里面读取缓存
        //2.redis中没有，就需要从数据库中获取
        //3.获取令牌
        semaphore.acquire();
        try {
            //4.从数据库中读取
            //5.存入redis
        } finally {
            semaphore.release();
        }

    }

    @Test
    public void testCode() {
        long code = getCode(GET_NEXT_CODE);
        log.info("code:{}", code);
    }

    @Test
    public void setStringTest() {
        stringRedisTemplate.opsForValue().set(VALUE_KEY, "I am Lvshen9");
        System.out.println("Test is OK");
    }

    @Test
    public void setHashTest() {
        stringRedisTemplate.opsForHash().put(HASH_KEY, "lvshen", "a value that is lvshen");
        System.out.println("Test is OK");
    }


    /**
     * spring原生获取
     *
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
