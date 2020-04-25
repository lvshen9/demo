package com.lvshen.demo.redis.ratelimiter;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/4/25 11:21
 * @since JDK 1.8
 */
@Aspect
@Component
@Slf4j
public class RateLimiterHandler {
    @Autowired
    private RedisTemplate redisTemplate;

    private DefaultRedisScript<Long> getRedisScript;

    @PostConstruct
    public void init() {
        getRedisScript = new DefaultRedisScript<>();
        getRedisScript.setResultType(Long.class);
        getRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("rateLimiter.lua")));
        log.info(">>>>>>>>>>>>>>>>RateLimiterHandler[分布式限流处理器] lua脚本加载完成");
    }

    @Pointcut("@annotation(com.lvshen.demo.redis.ratelimiter.RateLimiter)")
    public void rateLimiter() {}

    @Around("rateLimiter()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        log.info(">>>>>>>>>>>>>>>>RateLimiterHandler[分布式限流处理器]开始执行限流操作");
        /*Signature signature = point.getSignature();
        if (!(signature instanceof MethodSignature)) {
            throw new IllegalArgumentException("the Annotation @RateLimiter must used on method!");
        }*/
        MethodSignature signature = (MethodSignature) point.getSignature();
        /**
         * 获取注解参数
         */
        Method method = point.getTarget().getClass().getMethod(signature.getName(), signature.getMethod().getParameterTypes());
        RateLimiter rateLimiter = method.getAnnotation(RateLimiter.class);

        String limitKey = rateLimiter.key();
        Preconditions.checkNotNull(limitKey);

        long limitTimes = rateLimiter.limit();
        long expireTimes = rateLimiter.expire();

        log.info(">>>>>>>>>>>>>RateLimiterHandler[分布式限流处理器]参数值为-limitTimes={},limitTimeout={}", limitTimes, expireTimes);
        String message = rateLimiter.message();
        if (StringUtils.isBlank(message)) {
            message = "false";
        }

        //执行lua脚本
        List<String> keyList = Lists.newArrayList();
        keyList.add(limitKey);

        Long result = (Long) redisTemplate.execute(getRedisScript, keyList, expireTimes, limitTimes);

        StringBuffer sb = new StringBuffer();
        if ((result == 0)) {
            String msg = sb.append("超过单位时间=").append(expireTimes).append("允许的请求次数=").append(limitTimes).append("[触发限流]").toString();
            log.info(msg);
            return message;
        }

        return point.proceed();
    }


}
