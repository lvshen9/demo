package com.lvshen.demo.arithmetic.shorturl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Description: 长链转短链
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/3/23 9:55
 * @since JDK 1.8
 */
@Component
@Slf4j
public class ShortUrlUtil {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String SHORT_URL_KEY = "SHORT_URL_KEY";
    private static final String LOCALHOST = "http://t.cn/";
    private static final String SHORT_LONG_PREFIX = "short_long_prefix_";
    private static final String CACHE_KEY_PREFIX = "cache_key_prefix_";
    private static final int CACHE_SECONDS = 1 * 60 * 60;

    final static char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'v', 'W', 'x', 'Y',
            'z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'V', 'w', 'X', 'y', 'Z'
    };

    public enum Decimal {
        D32(32),
        D64(64);

        int x;

        Decimal(int x) {
            this.x = x;
        }
    }

    public String getShortUrl(String longUrl, Decimal decimal) {
        String cache = redisTemplate.opsForValue().get(CACHE_KEY_PREFIX + longUrl);
        if (cache != null) {
            log.info("从缓存【{}】中获取到：{}",CACHE_KEY_PREFIX + longUrl,cache);
            return LOCALHOST + toOtherBaseString(Long.valueOf(cache), decimal.x);
        }

        Long num = redisTemplate.opsForValue().increment(SHORT_URL_KEY);
        redisTemplate.opsForValue().set(SHORT_LONG_PREFIX + num, longUrl);

        redisTemplate.opsForValue().set(CACHE_KEY_PREFIX + longUrl, String.valueOf(num), CACHE_SECONDS, TimeUnit.SECONDS);
        return LOCALHOST + toOtherBaseString(num, decimal.x);
    }

    private String toOtherBaseString(long n, int base) {
        long num = 0;
        if (n < 0) {
            num = ((long) 2 * 0x7fffffff) + n + 2;
        } else {
            num = n;
        }
        char[] buf = new char[32];
        int charPos = 32;
        while ((num / base) > 0) {
            buf[--charPos] = digits[(int) (num % base)];
            num /= base;
        }
        buf[--charPos] = digits[(int) (num % base)];
        return new String(buf, charPos, (32 - charPos));
    }

}
