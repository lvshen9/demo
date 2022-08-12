package com.lvshen.demo.common;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import java.time.*;
import java.util.Collections;
import java.util.Date;
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

    /**
     * 时间戳转换Date
     *
     * @param dateStr 时间戳的字符串
     * @return
     */
    public static Date String2Date(String dateStr) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        long dateTimeLong = String2Time(dateStr);

        Instant timestamp = Instant.ofEpochMilli(dateTimeLong);
        ZonedDateTime losAngelesTime = timestamp.atZone(ZoneId.of("Asia/Shanghai"));
        LocalDateTime localDateTime = losAngelesTime.toLocalDateTime();
        return Date.from(localDateTime.atZone(ZoneOffset.ofHours(8)).toInstant());
    }

    public static long String2Time(String dateStr) {
        return Long.parseLong(dateStr);
    }

    /**
     * LocalDateTime转Date
     *
     * @param localDateTime
     * @return
     */
    public static Date localDateTimeConvertToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.toInstant(ZoneOffset.of("+8")));
    }
}
