package com.lvshen.demo.jsoup.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-9-8 14:34
 * @since JDK 1.8
 */
public class DateUtils {

    public static Date String2Date(String dateStr) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(dateStr, dtf);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date getCurrentDate() {
        Instant nowInstant = Instant.now();
        java.util.Date nowDate = Date.from(nowInstant);
        return nowDate;
    }
}
