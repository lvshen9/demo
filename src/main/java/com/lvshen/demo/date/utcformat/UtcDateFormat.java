package com.lvshen.demo.date.utcformat;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/6/24 9:08
 * @since JDK 1.8
 */
public class UtcDateFormat {
	public static void main(String[] args) {
		String dateStr = "2019-07-18T07:35:34.000Z";
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			// 拿到Date对象
			Date date = sdf1.parse(dateStr);
			// 输出格式：2017-01-22 09:28:33
			String str = sdf2.format(date);
			System.out.println(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
