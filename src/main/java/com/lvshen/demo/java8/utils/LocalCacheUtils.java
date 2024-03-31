package com.lvshen.demo.java8.utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.lvshen.demo.guava.study.cache.CustomizeRemovalListener;

import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2024/3/31 18:13
 * @since JDK 1.8
 */
public class LocalCacheUtils {
    /**
     * 本地缓存
     */
    private static Cache<String, Object> localCache = CacheBuilder.newBuilder()
            .maximumSize(100) // 设置缓存最大容量为100
            .expireAfterWrite(10, TimeUnit.MINUTES) // 设置写入后10分钟过期
            .build();


    public static Object getCache(String key) {
        return localCache.getIfPresent(key);
    }

    public static void setCache(String key,  Object value) {
        localCache.put(key, value);
    }
}
