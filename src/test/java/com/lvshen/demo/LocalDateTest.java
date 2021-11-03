package com.lvshen.demo;

import cn.hutool.core.lang.Console;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2021-10-29 16:06
 * @since JDK 1.8
 */
public class LocalDateTest {

    //获取今天的日期
    @Test
    public void getCurrentDate() {
        //获取的日期只到天
        LocalDate today = LocalDate.now();
        System.out.println("Today's Local date : " + today);

        //这个是作为对比
        Date date = new Date();
        System.out.println(date);

        //Today's Local date : 2021-10-29
        // Fri Oct 29 16:08:32 CST 2021
    }

    //获取年、月、日信息
    @Test
    public void getDetailDate() {
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();

        Console.log("Year : {}  Month : {}  day : {}", year, month, day);
        //Year : 2021  Month : 10  day : 29
    }

    //处理特定日期
    @Test
    public void handleSpecialDate() {
        LocalDate dateOfBirth = LocalDate.of(2021, 10, 29);
        System.out.println("The special date is : " + dateOfBirth);
    }

    //判断两个日期是否相等
    @Test
    public void compareDate() {
        LocalDate today = LocalDate.now();
        LocalDate date1 = LocalDate.of(2021, 10, 29);

        if (date1.equals(today)) {
            System.out.printf("TODAY %s and DATE1 %s are same date %n", today, date1);
        }
    }

    //处理周期性的日期
    @Test
    public void cycleDate() {

        //计算生日
        LocalDate today = LocalDate.now();
        LocalDate dateOfBirth = LocalDate.of(1995, 11, 02);

        MonthDay birthday = MonthDay.of(dateOfBirth.getMonth(), dateOfBirth.getDayOfMonth());
        MonthDay currentMonthDay = MonthDay.from(today);

        if (currentMonthDay.equals(birthday)) {
            System.out.println("Many Many happy returns of the day !!");
        } else {
            System.out.println("Sorry, today is not your birthday");
        }
    }

    //获取当前时间
    @Test
    public void getCurrentTime() {
        LocalDateTime todayDateTime = LocalDateTime.now();
        LocalDate todayDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        System.out.println("todayDateTime : " + todayDateTime);
        System.out.println("todayDate : " + todayDate);
        System.out.println("localTime :" + localTime);
    }

    //增加小时
    @Test
    public void plusHours() {
        LocalTime time = LocalTime.now();
        LocalTime newTime = time.plusHours(2); // 增加两小时
        System.out.println("Time after 2 hours : " + newTime);
    }

    //如何计算一周后的日期
    @Test
    public void nextWeek() {
        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plus(1, ChronoUnit.WEEKS);
        System.out.println("Today is : " + today);
        System.out.println("Date after 1 week : " + nextWeek);
    }

    //计算一年前或一年后的日期
    @Test
    public void minusDate() {
        LocalDate today = LocalDate.now();
        LocalDate previousYear = today.minus(1, ChronoUnit.YEARS);
        System.out.println("Date before 1 year : " + previousYear);

        LocalDate nextYear = today.plus(1, ChronoUnit.YEARS);
        System.out.println("Date after 1 year : " + nextYear);
    }

    @Test
    public void clock() {
        // 根据系统时间返回当前时间并设置为UTC。
        Clock clock = Clock.systemUTC();
        System.out.println("Clock : " + clock);

        // 根据系统时钟区域返回时间
        Clock defaultClock = Clock.systemDefaultZone();
        System.out.println("defaultClock : " + defaultClock);
    }

    //如何用Java判断日期是早于还是晚于另一个日期
    @Test
    public void isBeforeOrIsAfter() {
        LocalDate today = LocalDate.now();

        LocalDate tomorrow = LocalDate.of(2020, 1, 29);
        if (tomorrow.isAfter(today)) {
            System.out.println("Tomorrow comes after today");
        }

        LocalDate yesterday = today.minus(1, ChronoUnit.DAYS);

        if (yesterday.isBefore(today)) {
            System.out.println("Yesterday is day before today");
        }
    }

    //时区处理
    @Test
    public void getZoneTime() {
        //设置时区
        ZoneId america = ZoneId.of("America/New_York");

        LocalDateTime localDateAndTime = LocalDateTime.now();

        ZonedDateTime dateAndTimeInNewYork = ZonedDateTime.of(localDateAndTime, america);
        System.out.println("现在的日期和时间在特定的时区 : " + dateAndTimeInNewYork);
    }

    //使用 YearMonth类处理特定的日期
    @Test
    public void checkCardExpiry() {
        YearMonth currentYearMonth = YearMonth.now();
        System.out.printf("Days in month year %s: %d%n", currentYearMonth, currentYearMonth.lengthOfMonth());

        YearMonth creditCardExpiry = YearMonth.of(2028, Month.FEBRUARY);
        System.out.printf("Your credit card expires on %s %n", creditCardExpiry);
    }

    //检查闰年
    @Test
    public void isLeapYear() {
        LocalDate today = LocalDate.now();
        if (today.isLeapYear()) {
            System.out.println("This year is Leap year");
        } else {
            System.out.println("2018 is not a Leap year");
        }
    }

    //计算两个日期之间的天数和月数
    @Test
    public void calcDateDays() {
        LocalDate today = LocalDate.now();


        LocalDate destLocalDate = LocalDate.of(2018, Month.MAY, 14);

        Period period = Period.between(destLocalDate, today);


        Console.log("destLocalDate【{}】到today【{}】相差了【{}】年【{}】月【{}】天", destLocalDate, today, period.getYears(), period.getMonths(), period.getDays());

    }

    //两个日期之间的时间差
    @Test
    public void testBetween() {
        LocalDate today = LocalDate.now();
        LocalDate destLocalDate = LocalDate.of(2018, Month.MAY, 14);

        long needAddMs = ChronoUnit.DAYS.between(destLocalDate, today);
        System.out.println(needAddMs);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime datetime = LocalDateTime.of(2018, Month.MAY, 14, 00, 00);

        long needAddMs2 = ChronoUnit.MILLIS.between(datetime, now);
        System.out.println(needAddMs2);
    }


    // 包含时差信息的日期和时间
    public void ZoneOffset() {
        LocalDateTime datetime = LocalDateTime.of(2018, Month.FEBRUARY, 14, 19, 30);
        ZoneOffset offset = ZoneOffset.of("+05:30");
        OffsetDateTime date = OffsetDateTime.of(datetime, offset);
        System.out.println("Date and Time with timezone offset in Java : " + date);
    }

    // 获取时间戳
    @Test
    public void getTimestamp() {
        Instant timestamp = Instant.now();
        System.out.println("What is value of this instant " + timestamp);
    }

    // 使用预定义的格式化工具去解析或格式化日期
    public void formateDate() {
        String dayAfterTommorrow = "20180210";
        LocalDate formatted = LocalDate.parse(dayAfterTommorrow, DateTimeFormatter.BASIC_ISO_DATE);
        System.out.printf("Date generated from String %s is %s %n", dayAfterTommorrow, formatted);
    }
}
