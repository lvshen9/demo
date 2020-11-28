package com.lvshen.demo.guava.study.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-11-25 16:59
 * @since JDK 1.8
 */
public class CacheTest {
    @Test
    public void testCache() throws ExecutionException, InterruptedException {

        CacheLoader cacheLoader = new CacheLoader<String, Province>() {
            // 如果找不到元素，会调用这里
            @Override
            public Province load(String s) {
                //实际情况这里就是查库
                System.out.println("查了数据库哦");
                return new Province("查库DB","PR000");
            }
        };
        LoadingCache<String, Province> loadingCache = CacheBuilder.newBuilder()
                .maximumSize(1000) // 容量
                .expireAfterWrite(3, TimeUnit.SECONDS) // 过期时间
                .removalListener(new CustomizeRemovalListener()) // 失效监听器
                .build(cacheLoader); //
        loadingCache.put("PR001", new Province("北京", "PR001"));
        loadingCache.put("PR002", new Province("上海", "PR002"));
        loadingCache.put("PR003", new Province("深圳", "PR003"));

        System.out.println("PR001值：" + loadingCache.get("PR001"));
        loadingCache.invalidate("PR001"); // 手动失效
        System.out.println("设置手动失效后PR001值：" + loadingCache.get("PR001"));
        Province province = loadingCache.get("PR002");
        System.out.println("PR002值：" + province);
        Thread.sleep(4 * 1000);
        // 失效时间过去，获取为 null 值报错
        System.out.println("自动失效时间过去PR002的值" + loadingCache.get("PR002"));

    }

    @Test
    public void testCache2() throws ExecutionException, InterruptedException {

        CacheLoader cacheLoader = new CacheLoader<String, Province>() {
            // 如果找不到元素，会调用这里
            @Override
            public Province load(String s) {
                //实际情况这里就是查库
                System.out.println("查了数据库哦");
                return getDataFromDb();
            }
        };
        LoadingCache<String, Province> loadingCache = CacheBuilder.newBuilder()
                .maximumSize(1000) // 容量
                .expireAfterWrite(3, TimeUnit.SECONDS) // 过期时间
                .removalListener(new CustomizeRemovalListener()) // 失效监听器
                .build(cacheLoader);

        loadingCache.put("PR001", getDataFromDb());

        System.out.println("第一次获取：" + loadingCache.get("PR001"));
        System.out.println("第二次获取：" + loadingCache.get("PR001"));
        System.out.println("第三次获取：" + loadingCache.get("PR001"));
        Thread.sleep(4 * 1000);
        System.out.println("第四次获取：" + loadingCache.get("PR001"));

    }

    public Province getDataFromDb() {
        return new Province("北京", "PR001");
    }
}
