package com.lvshen.demo;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/5/23 20:22
 * @since JDK 1.8
 */
public class ContinueSiginTest {

	@org.junit.Test
	public void test() {
		List<String> days = getDays(new Date(), 3, 7); // 7天为周期，今天是第三天
		for (String day : days) {
			System.out.println(day);
		}
	}

	public List<String> getDays(Date date, int current, int cycle) {
		List<String> days = new ArrayList<String>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = getCalendar(date, current, cycle);
		for (int i = 0; i < cycle; i++) {
			days.add(format.format(calendar.getTime()));
			calendar.add(Calendar.DAY_OF_MONTH, 1);// 增加一天
		}
		return days;
	}

	public Calendar getCalendar(Date date, int current, int cycle) {
		if (current <= 0 || cycle <= 0 || date == null) {
			throw new IllegalArgumentException("参数异常");
		}

		Calendar now = Calendar.getInstance();
		Calendar calendarNow = new GregorianCalendar();
		calendarNow.setTime(date);

		int index = 1;
		current = current % cycle == 0 ? cycle : (current % cycle);
		index = index - current;

		calendarNow.add(Calendar.DAY_OF_MONTH, index);
		now.set(calendarNow.get(Calendar.YEAR), calendarNow.get(Calendar.MONTH), calendarNow.get(Calendar.DATE));

		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(calendarNow.get(Calendar.YEAR), calendarNow.get(Calendar.MONTH), calendarNow.get(Calendar.DATE));
		return calendar;

	}

}
