package com.lvshen.demo.yjh.java8.ch02;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Example1_Lambda {

	private static List<String> names = Arrays.asList("shekhar", "rahul", "sameer");

	@Test
	public void test01() {
		Collections.sort(names);
		System.out.println("names sorted alphabetically  >>");
		System.out.println(names);
	}

	@Test
	public void test02() {
		Collections.sort(names, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.length() - o2.length();
			}
		});
		System.out.println("names sorted by length  >>");
		System.out.println(names);
	}

	/**
	 * 使用lambda表达式
	 */
	@Test
	public void test03() {
		Collections.sort(names, (String first, String second) -> second.length() - first.length());

        System.out.println("names sorted by length(reversed)  >>");
        System.out.println(names);
	}

}
