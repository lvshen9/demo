package com.lvshen.demo.redis.ratelimiter;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.lvshen.demo.catchexception.BusinessException;
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
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Collections;
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
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private DefaultRedisScript<String> getRedisScript;

    @PostConstruct
    public void init() {
        getRedisScript = new DefaultRedisScript<>();
        getRedisScript.setResultType(String.class);
        getRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("rateLimiter.lua")));
        log.info(">>>>>>>>>>>>>>>>RateLimiterHandler[分布式限流处理器] lua脚本加载完成");
    }

    @Pointcut("@annotation(com.lvshen.demo.redis.ratelimiter.RateLimiter)")
    public void rateLimiter() {}

    @Around("rateLimiter()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        log.info(">>>>>>>>>>>>>>>>RateLimiterHandler[分布式限流处理器]开始执行限流操作");
        //获取类的字节码对象，通过字节码对象获取方法信息
        Class<?> targetCls = point.getTarget().getClass();
        //获取方法签名(通过此签名获取目标方法信息)

        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = targetCls.getMethod(signature.getName(), signature.getMethod().getParameterTypes());
        RateLimiter rateLimiter = method.getAnnotation(RateLimiter.class);

        String limitKey = rateLimiter.key();
        Preconditions.checkNotNull(limitKey);

        boolean needUserLimit = rateLimiter.needUserLimit();
        if (needUserLimit) {
            //获取目标方法名(目标类型+方法名)
            String targetClsName = targetCls.getName();
            String targetObjectMethodName = targetClsName + "." + signature.getName();
            Long userId = getCurrentUserId();
            Preconditions.checkNotNull(userId);
            limitKey = "redis:limit:".concat(targetObjectMethodName).concat(":").concat(String.valueOf(userId));
        }

        long limitTimes = rateLimiter.limit();
        long expireTimes = rateLimiter.expire();

        log.info(">>>>>>>>>>>>>RateLimiterHandler[分布式限流处理器]参数值为-limitTimes={},limitTimeout={}", limitTimes, expireTimes);

        //执行lua脚本
        String resultStr = stringRedisTemplate.execute(getRedisScript, Collections.singletonList(limitKey), String.valueOf(expireTimes), String.valueOf(limitTimes));
        long result = resultStr == null ? 0 : Long.parseLong(resultStr);
        StringBuilder sb = new StringBuilder();
        if (result == 0) {
            String msg = sb.append("超过单位时间=").append(expireTimes).append("允许的请求次数=").append(limitTimes).append("[触发限流]").toString();
            log.info("key:[{}]，{}", limitKey, msg);
            throw new BusinessException(String.format("您的操作过于频繁，请在%s秒后再进行操作", expireTimes));
        }

        return point.proceed();
    }

    private Long getCurrentUserId() {
        return 001001L;
    }


}
