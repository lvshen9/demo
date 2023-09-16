package com.lvshen.demo.java8.loacaldate;

import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/6/28 17:03
 * @since JDK 1.8
 */
public class LocalDateDemo {

    @Test
    public void test(){
        LocalDate today = LocalDate.now();
        System.out.println(today);

        int dayOfMonth = today.getDayOfMonth();
        System.out.println(dayOfMonth);

        int value = today.getDayOfWeek().getValue();
        System.out.println(value);
        System.out.println(today.getDayOfWeek());

        int dayOfYear = today.getDayOfYear();
        System.out.println(dayOfYear);

        LocalDate localDate = LocalDate.parse("2019-06-28");
        System.out.println(localDate);

    }

    @Test
    public void test2(){
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfMonth = today.with(TemporalAdjusters.firstDayOfMonth());
        System.out.println(firstDayOfMonth);

        LocalDate localDate = today.withDayOfMonth(2);
        System.out.println(localDate);

        LocalDate lastDayOdThisMonth = today.with(TemporalAdjusters.lastDayOfMonth());
        System.out.println(lastDayOdThisMonth);

        LocalDate firstDayOfNextMonth = today.plusDays(1);
        System.out.println(firstDayOfNextMonth);

        // 取2019年6月第一个周一：
        LocalDate firstMondayOf2019 = LocalDate.parse("2019-06-01").with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
        System.out.println(firstMondayOf2019);
    }

    @Test
    public void test3(){
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
        String format = today.format(formatter);
        System.out.println(format);
    }
}
