/*
package com.lvshen.demo.yjh.java8.mushroom;

import com.extendsTest.Student;
import com.google.common.collect.Lists;
import com.yjh.mushroom.utils.idgen.CodeGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

*/
/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/12/10 9:13
 * @since JDK 1.8
 *//*

@Slf4j
public class CodeGeneratorTest {

	@Test
	public void test1() {
		CodeGenerator codeGenerator = new CodeGenerator("HO", "yyMMddHHmmss", 4);
		String generateCode = codeGenerator.generateCode();
		log.info("generateCode: {}", generateCode);

	}

	@Test
	public void test2() {
		List<Student> lists = Lists.newArrayList();
		Student student = new Student();
		student.setName("小明");
		student.setGrade("1");
		student.setAge(11);

		Student student2 = new Student();
		student2.setName("小明");
		student2.setGrade("2");
		student2.setAge(22);

		Student student3 = new Student();
		student3.setName("小明");
		student3.setGrade("3");
		student3.setAge(33);

		Student student4 = new Student();
		student4.setName("卡卡");
		student4.setGrade("1");
		student4.setAge(11);

		lists.add(student);
		lists.add(student2);
		lists.add(student3);
		lists.add(student4);

		Map<String, Long> collect = lists.stream()
				.collect(Collectors.groupingBy(Student::getName, Collectors.counting()));
		log.info("collect", collect);
	}

	@Test
	public void test3() {
		log.info("TRUE:{}", Boolean.TRUE);
		log.info("true:{}", true);
	}

	@Test
	public void test4() {
		String str = "Hello";
		int result = 0;
		try {
			result = 1 / 0;
		} catch (Exception e) {

			log.error("错误:", e);
		}
	}

	@Test
	public void test5() {
		String resultStr = "5";
		int parseInt = Integer.parseInt(resultStr);
        Integer integer = Integer.valueOf(resultStr);
    }
}
*/
