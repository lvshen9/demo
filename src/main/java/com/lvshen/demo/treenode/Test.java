package com.lvshen.demo.treenode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/5/6 15:08
 * @since JDK 1.8
 */
public class Test {
	public static void main(String[] args) {
		List<Student> list1 = new ArrayList<>();
		list1.add(new Student(90,"xiaoming"));
		list1.add(new Student(80,"alan"));
		list1.add(new Student(70,"daniu"));
		list1.add(new Student(100,"daniu"));

		List<String> list2 = new ArrayList<>();
		list2.add("9");
		list2.add("4");
		list2.add("7");
		list2.add("8");

		Map<String, Student> studentMap = list1.stream().collect(Collectors.toMap(Student::getName, value -> value, (a1, a2) -> a1));
		System.out.println(studentMap);

		//List<String> intersection = list1.stream().filter(item -> list2.contains(item)).collect(toList());
		// System.out.println("---交集 intersection---");
		//System.out.println(intersection);
		//intersection.parallelStream().forEach(System.out::println);
		int sum = list1.stream().mapToInt(Student::getScore).sum();
		// System.out.println(sum);

	}
}
