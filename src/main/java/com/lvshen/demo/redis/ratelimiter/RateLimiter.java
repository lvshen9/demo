package com.lvshen.demo.redis.ratelimiter;

import java.lang.annotation.*;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/4/25 11:14
 * @since JDK 1.8
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter {

    /**
     * 限流key
     * @return
     */
    String key() default "rate:limiter";

    /**
     * 单位时间限制通过的请求数
     * @return
     */
    long limit() default 10;

    /**
     * 过期时间，单位s
     * @return
     */
    long expire() default 1;

    /**
     * 提示
     * @return
     */
    String message() default "false";
}
