package com.lvshen.demo.common;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2021-11-24 16:40
 * @since JDK 1.8
 */
public class MyStringUtils {

    public static String list2String(List<String> list, String separator) {
        if (CollectionUtils.isEmpty(removeNull(list))) {
            return StringUtils.EMPTY;
        }
        return Joiner.on(separator).join(list);
    }

    /**
     * 将String 按指定分隔符号分开并转换成List
     *
     * @param str
     * @param separator
     * @return
     */
    public static List<String> string2List(String str, String separator) {
        if (StringUtils.isBlank(str)) {
            return ImmutableList.of();
        }
        return Splitter.on(separator).omitEmptyStrings().trimResults().splitToList(str);
    }

    /**
     * null元素去除
     * @param oldList
     * @param <T>
     * @return
     */
    public static <T> List<T> removeNull(List<? extends T> oldList) {
        oldList.removeAll(Collections.singleton(null));
        return (List<T>) oldList;
    }
}
