package com.lvshen.demo.arithmetic.stack;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.Stack;

/**
 * Description: 是否回文数判断
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/11/4 16:21
 * @since JDK 1.8
 */
@Slf4j
public class StackArithmetic {

	@Test
	public void test() {
		String param = "席主席";
		System.out.println(palindromeTest(param));
	}

	@Test
	public void test2() {
		logTest2();
	}

	@Test
	public void test3() {
		try {
			int a = 1 / 1;
		} catch (Exception e) {
            throw new RuntimeException();
		}
        System.out.println("1111");
	}

	private boolean palindromeTest(String param) {
		if (StringUtils.isBlank(param)) {
			return false;
		}
		Stack<String> stack = new Stack<>();
		char[] chars = param.toCharArray();
		int mid = chars.length / 2;

		for (int i = 0; i < mid; i++) {
			stack.push(String.valueOf(chars[i]));
		}

		for (int i = mid + 1; i <= chars.length - 1; i++) {
			// 栈首获取
			if (!stack.peek().equals(String.valueOf(chars[i]))) {
				break;
			}
			// 出栈
			stack.pop();
		}
		if (stack.size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public void logTest2() {
		logTest();
	}

	public void logTest() {
		log.info("当前方法：{}", Thread.currentThread().getStackTrace()[1].getMethodName());
	}
}
