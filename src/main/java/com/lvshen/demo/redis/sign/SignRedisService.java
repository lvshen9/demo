package com.lvshen.demo.redis.sign;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/9/17 20:58
 * @since JDK 1.8
 */
public class SignRedisService {

    public Jedis jedis = getResource();

    public Jedis getResource() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(8);
        config.setMaxTotal(18);
        JedisPool pool = new JedisPool(config, "192.168.42.128", 6379, 2000, "lvshen");
        Jedis jedis = pool.getResource();
        return jedis;
    }

    /**
     * 签到
     * @param userId
     * @param date
     * @return
     */
    public boolean doSign(int userId, LocalDate date) {
        int offset = date.getDayOfMonth() - 1;
        return jedis.setbit(buildSignKey(userId, date),offset,true);
    }

    /**
     * 检查用户当天是否签到
     * @param uid
     * @param date
     * @return
     */
    public boolean checkSign(int uid, LocalDate date) {
        int offset = date.getDayOfMonth() - 1;
        return jedis.getbit(buildSignKey(uid, date), offset);
    }

    public long getContinueSignCount(int userId, LocalDate date) {
        int signCount = 0;
        String type = String.format("u%d", date.getDayOfMonth());

        List<Long> list = jedis.bitfield(buildSignKey(userId, date), "GET", type, "0");
        if (CollectionUtils.isNotEmpty(list)) {
            // 取低位连续不为0的个数即为连续签到次数，需考虑当天尚未签到的情况
            long value = list.get(0) == null ? 0 : list.get(0);
            int bound = date.getDayOfMonth();
            for (int i = 0; i < bound; i++) {
                if (value >> 1 << 1 == value) {
                    // 低位为0且非当天说明连续签到中断了
                    if (i > 0) {
                        break;
                    }
                } else {
                    signCount += 1;
                }
                value >>= 1;
            }
        }
        return signCount;
    }

    /**
     * 获取当月签到情况
     *
     * @param userId  用户ID
     * @param date 日期
     * @return Key为签到日期，Value为签到状态的Map
     */
    public Map<String, Boolean> getSignInfo(int userId, LocalDate date) {
        Map<String, Boolean> signMap = new HashMap<>(date.getDayOfMonth());
        String type = String.format("u%d", date.lengthOfMonth());
        List<Long> list = jedis.bitfield(buildSignKey(userId, date), "GET", type, "0");
        if (list != null && list.size() > 0) {
            // 由低位到高位，为0表示未签，为1表示已签
            long v = list.get(0) == null ? 0 : list.get(0);
            for (int i = date.lengthOfMonth(); i > 0; i--) {
                LocalDate d = date.withDayOfMonth(i);
                signMap.put(formatDate(d, "yyyy-MM-dd"), v >> 1 << 1 != v);
                v >>= 1;
            }
        }
        return signMap;
    }

    private static String buildSignKey(int uid, LocalDate date) {
        return String.format("sign:%d:%s", uid, formatDate(date));
    }

    private static String formatDate(LocalDate date) {
        return formatDate(date, "yyyyMM");
    }

    private static String formatDate(LocalDate date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }


    @Test
    public void testDoSign() {
        SignRedisService service = new SignRedisService();
        LocalDate today = LocalDate.now();
        LocalDate localDate = today.minusDays(7);
        boolean signed = service.doSign(1, localDate);
        if (signed) {
            System.out.println("您已签到：" + formatDate(localDate, "yyyy-MM-dd"));
        } else {
            System.out.println("签到完成：" + formatDate(localDate, "yyyy-MM-dd"));
        }
    }

    @Test
    public void getContinueSign() {
        SignRedisService service = new SignRedisService();
        LocalDate today = LocalDate.now();
        long continueSignCount = service.getContinueSignCount(1, today);
        System.out.println("连续签到次数："+continueSignCount);
    }

    @Test
    public void getSignInfo() {
        System.out.println("当月签到情况：");
        SignRedisService service = new SignRedisService();
        LocalDate today = LocalDate.now();
        Map<String, Boolean> signInfo = new TreeMap<>(service.getSignInfo(1, today));
        for (Map.Entry<String, Boolean> entry : signInfo.entrySet()) {
            System.out.println(entry.getKey() + ": " + (entry.getValue() ? "√" : "-"));
        }
    }
}
