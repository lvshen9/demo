package com.lvshen.demo.date;

import org.junit.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/7/3 9:57
 * @since JDK 1.8
 */
public class DataDemoTes {

	@Test
	public void test() {
        DateEntity dateEntity = new DateEntity();
        Date now = new Date();
        dateEntity.setEndDate(now);
        System.out.println(dateEntity.getEndDate());
        //改变了now的值
        now.setYear(5);
        System.out.println(dateEntity.getEndDate());
    }

    @Test
    public void test1(){
        DateEntity dateEntity = new DateEntity();
        Calendar calendar = Calendar.getInstance();
        System.out.println("1: " + calendar.getTime().toString());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Date time = calendar.getTime();
        dateEntity.setEndDate(time);
        System.out.println("time: " + time.toString());
        System.out.println("2: " + dateEntity.getEndDate());
    }

    @Test
    public void test2() {
        /*Date date = DateTimeUtils.getFormatDate("2019-07-25");
        System.out.println(date);*/
    }

    @Test
    public void test3() throws ParseException {
        Date startDate = getDate("2019-10-04T16:00:00.000Z");
        Date endDate = getDate("2019-10-05T15:59:59.000Z");

        /*BigDecimal daysOfTwoDate = DateTimeUtils.getDaysOfTwoDate(startDate, endDate);
        System.out.println(daysOfTwoDate);*/
    }

    public Date getDate(String dateStr) throws ParseException {
        dateStr = dateStr.replace("Z", " UTC");//是空格+UTC
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");//格式化的表达式
        return format.parse(dateStr);
    }
}
