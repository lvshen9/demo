package com.lvshen.demo.annotation.easyexport.utils;

import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-12-20 14:34
 * @since JDK 1.8
 */
public class ThreadLocalContext {
    private static ThreadLocal<Map<String, Object>> context = new ThreadLocal();
    public static String getStringValue(String key) {
        return context.get() == null ? null : Objects.toString(((Map<?, ?>)context.get()).get(key), null);
    }

    public static boolean exists(String key) {
        return context.get() != null && context.get().containsKey(key);
    }

    public static <T> T get(String key) {
        return context.get() == null ? null : (T) ((Map<?, ?>)context.get()).get(key);
    }
    public static void set(String key, Object value) {
        if (value != null) {
            getContextMap().put(key, value);
        }
    }
    private static Map getContextMap() {
        if (context.get() == null) {
            context.set(Maps.newHashMap());
        }

        return context.get();
    }
}
