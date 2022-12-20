package com.lvshen.demo.redis;

import com.alibaba.excel.util.DateUtils;
import com.lvshen.demo.annotation.easyexport.utils.CurrentRuntimeContext;
import com.lvshen.demo.annotation.sensitive.SpringContextHolder;
import com.lvshen.demo.catchexception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.text.NumberFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-12-20 15:28
 * @since JDK 1.8
 */
public class CodeUtils {
    private static final RedisUtils redisUtils = SpringContextHolder.getBean(RedisUtils.class);
    private static final RedissonClientConfig redissonService = SpringContextHolder.getBean(RedissonClientConfig.class);

    private static final String GET_NEXT_CODE_KEY = "demo:get_code:%s:%s_%s:%s";
    private static final String REDIS_LOCK = "demo_code_generator_lock_";
    private static final String COMMON_TENANT_ID = "1000000";

    public static String generateFullCode(String prefix, int digit) {
        return generateFullCode(prefix, "yyyyMMdd", "GENERAL_CODE", true, digit);
    }

    /**
     * 单号生成器
     * 通用单号生成器   格式 前缀 + YYMMDD + 序号
     * * 列如    generatorCode("D","LVSHEN",4)，当前日期为：2022-08-12
     * * 生成为：D202208120001
     *
     * @param prefix 前缀
     * @param module 业务模块
     * @param digit  编码位数
     * @return
     */
    public static String generateFullCode(String prefix, String module, int digit) {
        return generateFullCode(prefix, "yyyyMMdd", module, true, digit);
    }

    public static String generateFullCode(String prefix, String datePattern, String module, boolean validateTenantId, int digit) {
        String suffixCodeStr = getSuffixCodeStr(prefix, datePattern, module, validateTenantId, digit);
        String todayStr = getTodayStr(datePattern);
        String[] codes = {prefix, todayStr, suffixCodeStr};
        return String.join("", codes);
    }

    private static String getSuffixCodeStr(String prefix, String datePattern, String module, boolean validateTenantId, int digit) {
        String redisKey = getRedisKey(prefix, datePattern, module, validateTenantId);
        long code = generatorCode(redisKey);
        return getDigitNumber((int) code, digit);
    }

    private static String getDigitNumber(int number, int digit) {
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMinimumIntegerDigits(digit);
        formatter.setGroupingUsed(false);
        return formatter.format(number);
    }

    public static long generatorCode(String key) {
        RedissonClient redissonClient = redissonService.getRedissonClient();
        RAtomicLong atomicVar = redissonClient.getAtomicLong(key);
        RLock lock = redissonClient.getLock(REDIS_LOCK.concat(key));
        long value = 0;
        try {
            if (lock.tryLock(10, 10, TimeUnit.SECONDS)) {
                if (!atomicVar.isExists()) {
                    atomicVar.set(0);
                    redisUtils.expire(key, 2 * 24 * 60 * 60);
                }
                value = atomicVar.incrementAndGet();
            }
        } catch (Exception e) {
            throw new BusinessException(500, String.format("单号生成异常,key:[%s]", key));
        } finally {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
        return value;
    }
    private static String getRedisKey(String prefix, String datePattern, String module, boolean validateTenantId) {
        String tenantId = CurrentRuntimeContext.getTenantId(validateTenantId);
        if (StringUtils.isBlank(tenantId) || !validateTenantId) {
            tenantId = COMMON_TENANT_ID;
        }
        String todayStr = getTodayStr(datePattern);
        return String.format(GET_NEXT_CODE_KEY, tenantId, prefix, module, todayStr);
    }

    private static String getTodayStr(String datePattern) {
        return DateUtils.format(new Date(), datePattern);
    }

    public static String getSupplierCode(String supplierCode) {
        return StringUtils.leftPad(supplierCode, 10, '0');
    }


}
