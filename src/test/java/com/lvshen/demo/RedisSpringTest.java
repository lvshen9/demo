package com.lvshen.demo;

import com.lvshen.demo.redis.ratelimiter.RateLimiter;
import com.lvshen.demo.redis.reentrantlock.RedisDelayingQueue;
import com.lvshen.demo.redis.reentrantlock.RedisWithReentrantLock;
import com.lvshen.demo.redis.subscribe.GoodsMessage;
import com.lvshen.demo.redis.subscribe.Publisher;
import com.lvshen.demo.redis.subscribe.UserMessage;
import lombok.extern.slf4j.Slf4j;
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

import java.util.UUID;
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

    //@Autowired
    //private ShortUrlUtil shortUrlUtil;

    @Autowired
    private Publisher publisher;

    @Autowired
    private RedisWithReentrantLock redisWithReentrantLock;

    @Autowired
    private RedisDelayingQueue<String> delayingQueue;


    private static final String MESSAGE = "{\"code\":\"400\",\"msg\":\"FAIL\",\"desc\":\"触发限流\"}";

    //模拟布隆过滤器
    @Test
    public void testBit() {
        String userId = "10001001001";
        int hasValue = Math.abs(userId.hashCode()); //key 做hash运算
        long index = (long) (hasValue % Math.pow(2, 32)); //hash值与数组长度取模
        Boolean bloomFilter = redisTemplate.opsForValue().setBit("user_bloom_filter", index, true);
        log.info("user_bloom_filter:{}", bloomFilter);
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
            log.info("该userId在数据库中不存在：{}", userId);
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

    //redis可重入锁测试
    @Test
    public void testRLock() {
        String KEY = "r_lock";
        System.out.println("进入锁" + redisWithReentrantLock.lock(KEY));
        System.out.println("第二次进入" + redisWithReentrantLock.lock(KEY));

        System.out.println("第一次退出" + redisWithReentrantLock.unlock(KEY));
        System.out.println("第二次退出" + redisWithReentrantLock.unlock(KEY));
    }

    //多线程测试
    @Test
    public void testRockWithThread() {
        String key = "r_lock";
        new Thread(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getName() + "进入锁" + redisWithReentrantLock.lock(key));
            System.out.println("当前线程：" + Thread.currentThread().getName() + "第二次进入" + redisWithReentrantLock.lock(key));
        }).start();


        System.out.println("当前线程：" + Thread.currentThread().getName() + "进入锁" + redisWithReentrantLock.lock(key));


    }

    //延迟队列测试
    @Test
    public void testDelayQueue() {
        String queueKey = "redis_delay_queue";
        delayingQueue.setQueueKey(queueKey);
        Thread producer = new Thread(() -> delayingQueue.delay("I am Lvshe", 10000));

        Thread consumer = new Thread(() -> delayingQueue.loop());

        producer.start();
        consumer.start();

        try {
            producer.join();

            Thread.sleep(20000);
            consumer.interrupt();
            consumer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
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

    @Test
    public void testShortUrl() {
        String url = "www.google.com";

        //String shortUrl = shortUrlUtil.getShortUrl(url, ShortUrlUtil.Decimal.D32);
        //System.out.println("短链：" + shortUrl);
    }

    //redis发布订阅 pub/sub
    @Test
    public void pushMessage() {
        UserMessage userMessage = new UserMessage();
        userMessage.setMsgId(UUID.randomUUID().toString().replace("-", ""));
        userMessage.setUserId("1");
        userMessage.setUsername("admin");
        userMessage.setUsername("root");
        userMessage.setCreateStamp(System.currentTimeMillis());
        publisher.pushMessage("user", userMessage);
        GoodsMessage goodsMessage = new GoodsMessage();
        goodsMessage.setMsgId(UUID.randomUUID().toString().replace("-", ""));
        goodsMessage.setGoodsType("苹果");
        goodsMessage.setNumber("十箱");
        goodsMessage.setCreateStamp(System.currentTimeMillis());
        publisher.pushMessage("goods", goodsMessage);
    }

    @Test
    public void test() {
        for (int i = 0; i < 10; i++) {
            System.out.println(limitFun());
        }
    }

    /**
     * 10s内限制请求5次
     * @return
     */
    @RateLimiter(key = "ratedemo:1.0.0", limit = 5, expire = 10, message = MESSAGE)
    private String limitFun() {
        //System.out.println("正常请求");
        return "正常请求";
    }
}
