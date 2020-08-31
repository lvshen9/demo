package com.lvshen.demo.redis.cache;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/3/21 21:23
 * @since JDK 1.8
 */
@Component
@Aspect
@Slf4j
public class CacheAspect {
    @Autowired
    private RedisTemplate redisTemplate;

    @Pointcut("@annotation(com.lvshen.demo.redis.cache.CustomizeCache)")
    public void cachePointcut() {
    }

    @Around("cachePointcut()")
    public Object doCache(ProceedingJoinPoint point) {
        Object value = null;
        Semaphore semaphore = null;
        MethodSignature signature = (MethodSignature) point.getSignature();

        try {
            //获取方法上注解的类容
            Method method = point.getTarget().getClass().getMethod(signature.getName(), signature.getMethod().getParameterTypes());
            CustomizeCache annotation = method.getAnnotation(CustomizeCache.class);
            String keyEl = annotation.key();
            String prefix = annotation.value();
            long expireTimes = annotation.expireTimes();
            int semaphoreCount = annotation.semaphoreCount();

            //解析SpringEL表达式
            SpelExpressionParser parser = new SpelExpressionParser();
            Expression expression = parser.parseExpression(keyEl);
            StandardEvaluationContext context = new StandardEvaluationContext();

            //添加参数
            Object[] args = point.getArgs();
            DefaultParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();
            String[] parameterNames = discoverer.getParameterNames(method);
            for (int i = 0; i < parameterNames.length; i++) {
                context.setVariable(parameterNames[i], args[i].toString());
            }

            //解析
            String key = prefix + "::" + expression.getValue(context).toString();

            //判断缓存中是否存在
            value = redisTemplate.opsForValue().get(key);
            if (value != null) {
                log.info("从缓存中读取到值：{}", value);
                return value;
            }

            //自定义组件，如：限流，降级。。。
            //创建限流令牌
            semaphore = new Semaphore(semaphoreCount);
            boolean tryAcquire = semaphore.tryAcquire(3000L, TimeUnit.MILLISECONDS);
            if (!tryAcquire) {
                //log.info("当前线程【{}】获取令牌失败,等带其他线程释放令牌", Thread.currentThread().getName());
                throw new RuntimeException(String.format("当前线程【%s】获取令牌失败,等带其他线程释放令牌", Thread.currentThread().getName()));
            }

            //缓存不存在则执行方法
            value = point.proceed();

            //同步value到缓存
            redisTemplate.opsForValue().set(key, value, expireTimes, TimeUnit.SECONDS);


        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (semaphore == null) {
                return value;
            } else {
                semaphore.release();
            }
        }
        return value;
    }
}
