package com.lvshen.demo.guava.study.collections;

import org.junit.Test;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/6/21 10:57
 * @since JDK 1.8
 */
public class ClassToInstanceMapDemo {

	@Test
	public void test() {
		MutableClassToInstanceMap<Object> classToInstanceMap = MutableClassToInstanceMap.create();
		classToInstanceMap.put(String.class, "lvshen");
		System.out.println(classToInstanceMap.getInstance(String.class));

        ClassToInstanceMap<Person> classToInstanceMap2 = MutableClassToInstanceMap.create();
        Person person = new Person("peida", 20);
        System.out.println("person name=" + person.name + "，age=" + person.age);
        // 存
        classToInstanceMap2.putInstance(Person.class, person);

        Person person1 = classToInstanceMap2.getInstance(Person.class);
        System.out.println("person1 name=" + person1.name + "，age=" + person1.age);
	}

	class Person {
		public String name;
		public int age;

		Person(String name, int age) {
			this.age = age;
			this.name = name;
		}
	}
}
