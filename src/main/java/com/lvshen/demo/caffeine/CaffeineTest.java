package com.lvshen.demo.caffeine;




import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalListener;
import org.junit.Test;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-12-28 14:40
 * @since JDK 1.8
 */
public class CaffeineTest {
    public static void main(String[] args) {
        Cache<String, String> cache = Caffeine.newBuilder()
                // 数量上限
                .maximumSize(1024)
                // 过期机制
                .expireAfterWrite(5, TimeUnit.MINUTES)
                // 弱引用key
                .weakKeys()
                // 弱引用value
                .weakValues()
                // 剔除监听
                .removalListener((RemovalListener<String, String>) (key, value, cause) ->
                        System.out.println("key:" + key + ", value:" + value + ", 删除原因:" + cause.toString()))
                .build();

        String valueFromCache = cache.get("lvshen", key -> getValue(key));

        String valueFromCache2 = cache.get("lvshen", key -> getValue(key));

        System.out.println("测试结果："+ valueFromCache);

        cache.invalidate("lvshen");
        cache.put("Lvshen","Hello");

        System.out.println("测试结果2："+ valueFromCache2);


    }

    /**
     * 模拟数据库
     */
    public static String getValue(String key) {
        System.out.println("调用了getValue方法....");
        return key;
    }

    @Test
    public void testDate() {
        Date date = new Date();
        DateTime dateTime = DateUtil.beginOfDay(date);

        DateTime endOfDay = DateUtil.endOfDay(date);

        DateTime offset = DateUtil.offset(date, DateField.DAY_OF_YEAR, -20);
        System.out.println(dateTime);

        System.out.println(endOfDay);

        System.out.println(offset);
    }
}
