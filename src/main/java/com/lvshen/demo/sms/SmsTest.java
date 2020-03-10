package com.lvshen.demo.sms;

import org.junit.Test;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/9/18 15:50
 * @since JDK 1.8
 */
public class SmsTest {

	@Test
	public void test1() {
		String s = MD5Util.MD5(getStr());
		s = convertToLowerCase(s);
		System.out.println("MD5：" + s);
		System.out.println(s.length());
	}

	private String getStr() {
		String userName = "18670027203";
		String password = "zxcvbn";
		long time = Instant.now().getEpochSecond();
		System.out.println("time: " + time);
		StringBuilder sb = new StringBuilder();

		return sb.append(userName).append(password).append(time).toString();
	}

	public String convertToLowerCase(String str) {
		List<String> list = Arrays.asList(str);
		String[] words = str.split("\\s+");
		List<String> initUpperWords = list.stream().map(word -> word.toLowerCase()).collect(Collectors.toList());
		String finalWord = String.join(" ", initUpperWords);
		return finalWord;
	}

}
