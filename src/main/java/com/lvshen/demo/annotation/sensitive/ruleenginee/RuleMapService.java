package com.lvshen.demo.annotation.sensitive.ruleenginee;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2021/5/4 17:31
 * @since JDK 1.8
 */
public class RuleMapService {
    public static Map<String, String> getMap() {
        return map;
    }

    private static Map<String,String> map = Maps.newHashMap();
}
