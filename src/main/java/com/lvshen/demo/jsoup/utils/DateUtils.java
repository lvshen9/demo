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
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy");
        LocalDateTime localDateTime = LocalDateTime.parse(dateStr, dtf);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date getCurrentDate() {
        Instant nowInstant = Instant.now();
        java.util.Date nowDate = Date.from(nowInstant);
        return nowDate;
    }

    public static boolean beforeDate(Date currentDate, Date desDate) {
        if (currentDate.getTime() < desDate.getTime()) {
            return true;
        }
        return false;
    }

    public static boolean afterDate(Date currentDate, Date desDate) {
        if (currentDate.getTime() > desDate.getTime()) {
            return true;
        }
        return false;
    }

    public static boolean betweenDate(Date currentDate, Date startDate,Date endDate) {
        boolean isAfter = currentDate.getTime() > startDate.getTime() || currentDate.getTime() == startDate.getTime();
        boolean isBefore = currentDate.getTime() < endDate.getTime() || currentDate.getTime() == endDate.getTime();
        if (isAfter && isBefore) {
            return true;
        }
        return false;
    }
}
