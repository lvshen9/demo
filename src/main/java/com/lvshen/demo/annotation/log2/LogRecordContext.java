package com.lvshen.demo.annotation.log2;

import com.google.common.collect.Maps;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2024/3/31 19:11
 * @since JDK 1.8
 */
public class LogRecordContext {

    private static final ThreadLocal<Map<String, String>> THREAD_LOCAL = new ThreadLocal<>();

    public static void setVariable(String key, String value) {
        Map<String, String> stringObjectMap = THREAD_LOCAL.get();
        if (MapUtils.isEmpty(stringObjectMap)) {
            stringObjectMap = Maps.newHashMap();
            THREAD_LOCAL.set(stringObjectMap);
        }
        THREAD_LOCAL.get().put(key, value);
    }

    public static String getVariable(String key) {
        Map<String, String> stringObjectMap = THREAD_LOCAL.get();
        if (MapUtils.isEmpty(stringObjectMap)) {
            return StringUtils.EMPTY;
        }
        return stringObjectMap.get(key);
    }

    public static Map<String, String> getVariables() {
        return THREAD_LOCAL.get();
    }

    public static void clear() {
        Map<String, String> stringObjectMap = THREAD_LOCAL.get();
        if (MapUtils.isNotEmpty(stringObjectMap)) {
            stringObjectMap.clear();
        }
        THREAD_LOCAL.remove();
    }
}
