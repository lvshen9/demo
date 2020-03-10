package com.lvshen.demo.yjh.java8.foreachtest;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/7/4 11:58
 * @since JDK 1.8
 */
public class TestForEach {

	@Test
	public void test1() {
		List<String> list = new ReverseList();
		List<String> asList = Arrays.asList("A", "B", "C", "D");
		list.addAll(asList);
		System.out.println(list);

		list.forEach(System.out::print);
        System.out.print(" ");
		for (String a : list) {
            System.out.print(a);
		}
		System.out.print(" ");
		list.stream().forEach(System.out::print);
	}
}
