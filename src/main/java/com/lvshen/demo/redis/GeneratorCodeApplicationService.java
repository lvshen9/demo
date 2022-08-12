package com.lvshen.demo.redis;

import cn.hutool.core.date.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.NumberFormat;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-8-11 19:12
 * @since JDK 1.8
 */
@Component
public class GeneratorCodeApplicationService {
    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private RedissonClientConfig redissonService;

    private static final String GET_NEXT_CODE_KEY = "redis_get_code";

    private static final String CODE_DATE_RECORD_KEY = "_date_record";

    private String getRedisKey(String prefixToUpperCase, String prefix) {
        StringBuilder sb = new StringBuilder();
        sb.append(GET_NEXT_CODE_KEY).append("::");
        if (StringUtils.isNotBlank(prefix)) {
            sb.append(prefix).append("::");
        }
        sb.append(prefixToUpperCase);
        return sb.toString();
    }

    /**
     * 通用单号生成器   格式 前缀 + YYMMDD + 序号
     * 列如    generatorCode("D",4)，当前日期为：2022-08-12
     * 生成为：D202208120001
     * @param prefix 前缀
     * @param digit  序号  输入数字几，则生成几位序号
     * @return
     */
    public String generatorCode(String prefix, int digit) {
        String redisKey = getRedisKey(prefix, "GENERAL_CODE");
        return generateCode(prefix, redisKey, digit);
    }

    /**
     * 编号生成方法
     *
     * @param prefix
     * @return
     */
    private String generateCode(String prefix, String redisKey, int digit) {
        String dateStr = getTodayStr();

        String suffixCodeStr = getSuffixCodeStr(redisKey, digit);

        String[] codes = {prefix, dateStr, suffixCodeStr};
        return String.join("", codes);
    }

    private String getTodayStr() {
        return DateUtil.format(new Date(), "yyyyMMdd");
    }

    private void saveCodeRecord(String key, String value) {
        String recordKey = key + CODE_DATE_RECORD_KEY;
        redisUtils.set(recordKey, value);
    }

    private String getCodeRecord(String key) {
        String recordKey = key + CODE_DATE_RECORD_KEY;
        return (String) redisUtils.get(recordKey);
    }

    private boolean isSameDay(String today, String codeRecordDate) {
        return today.equals(codeRecordDate);
    }
    /**
     * 编号后缀获取 第二天后缀顺序号重新置为1
     *
     * @param key
     * @return
     */
    private synchronized long getSuffixCode(String key) {
        RedissonClient redissonClient = redissonService.getRedissonClient();
        RAtomicLong atomicVar = redissonClient.getAtomicLong(key);
        String todayStr = getTodayStr();
        String codeRecord = getCodeRecord(key);
        if (!atomicVar.isExists()) {
            atomicVar.set(1);
        }
        if (StringUtils.isNotBlank(codeRecord)) {
            if (!isSameDay(todayStr, codeRecord)) {
                atomicVar.set(1);
            }
        }
        saveCodeRecord(key, todayStr);
        long value = atomicVar.incrementAndGet();
        return value;
    }

    private String getSuffixCodeStr(String key, int digit) {
        long code = getSuffixCode(key);
        return getDigitNumber((int) code, digit);
    }

    public String getDigitNumber(int number, int digit) {
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMinimumIntegerDigits(digit);
        formatter.setGroupingUsed(false);
        return formatter.format(number);
    }
}
