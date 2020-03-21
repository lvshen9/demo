package com.lvshen.demo.redis.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description:自定义缓存注解
 * 方法前先判断缓存 ，之后将值还回，并设置缓存
 * @author Lvshen
 * @version 1.0
 * @date: 2020/3/21 21:19
 * @since JDK 1.8
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomizeCache {

    String key();

    String value();

    long expireTimes() default 120L; //默认过期时间120s

    int semaphoreCount() default Integer.MAX_VALUE;  //默认限制线程并发数
}
