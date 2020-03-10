package com.lvshen.demo.stringbuildertest;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/8/19 15:22
 * @since JDK 1.8
 */
public class StringBuilderTest {

	@Test
	public void test1() {
		String str1 = "love";
		long currentTimeMillis1 = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			// 系统会在这里创建StringBuilder，然后进行append,这样会增加内存消耗
			str1 += i;
		}
		System.out.println("直接拼接耗时：" + (System.currentTimeMillis() - currentTimeMillis1));
		// StringBuilder
		long currentTimeMillis2 = System.currentTimeMillis();
		StringBuilder str2 = new StringBuilder("love2");
		for (int i = 0; i < 100000; i++) {
			// 这里的StringBuilder是在外部创建的，就一个，所以不会增加内存消耗
			str2.append(i);
		}
		System.out.println("StringBuilder拼接耗时：" + (System.currentTimeMillis() - currentTimeMillis2));
	}

	@Test
	public void test2() throws ParseException {
		/*Date oldTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2019-06-09");
		oldTime = DateTimeUtils.getDateFirstTime(oldTime);
		Date nowTime = new Date();
		nowTime = DateTimeUtils.getDateFirstTime(nowTime);
		//可以判断两时间点是不是连续两天
		long times = ChronoUnit.HOURS.between(oldTime.toInstant(), nowTime.toInstant());
		System.out.println(times);*/

	}
}
