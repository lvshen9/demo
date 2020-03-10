package com.lvshen.demo.guava.study.basicutilities;

import lombok.Data;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/6/27 14:35
 * @since JDK 1.8
 */
@Data
public class Employee {
	private String name;
	private int age;

	public Employee(String name, int age) {
        this.name = name;
        this.age = age;
	}
}
