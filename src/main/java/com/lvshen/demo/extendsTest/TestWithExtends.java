package com.lvshen.demo.extendsTest;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/8/15 9:57
 * @since JDK 1.8
 */
public class TestWithExtends {

	@Test
	public void test() {
        Student student = new Student();
        testType(student);

        System.out.println("++++++++++++++++++++++");

        Person person = new Person();
        testType(person);
    }

    @Test
	public void test2() {
		List<String> str = new ArrayList<>();
		str.add("");
		System.out.println(str.size());
	}

	private void testType(Object o) {
		if (o instanceof Person) {
			System.out.println("this is person");
		} else if (o instanceof Student) {
			System.out.println("this is student");
		}

	}
}
