package com.lvshen.demo.guava.study.basicutilities;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/6/27 14:53
 * @since JDK 1.8
 */
public class OptionalDemo {

	@Test
	public void test() {
		Integer i = null;
		Optional<Integer> optional = Optional.fromNullable(i);
		if (optional.isPresent()) {
			System.out.println(optional.get());
		} else {
			System.out.println(0);
		}

		Integer numberOne = null;
		// Optional<Integer> of1 = Optional.of(numberOne);
		Optional<Object> absent = Optional.absent();

	}
	@Test
	public void test1() {
        Optional<Integer> nulloptional = Optional.absent();// null就是缺少的意思
        Integer value1 = nulloptional.or(3);
        System.out.println("value1 = " + value1);

        Optional<Integer> optional = Optional.of(2);
        Integer value2 = optional.or(3);
        System.out.println("value2 = " + value2);
	}

    @Test
    public void test2() {
        Optional<Integer> nulloptional = Optional.absent();// null就是缺少的意思
        Integer value1 = nulloptional.orNull();
        System.out.println("value1 = " + value1);

        Optional<Integer> optional = Optional.of(5);
        Integer value2 = optional.orNull();
        System.out.println("value2 = " + value2);
    }


    /**
     * 计算所有员工总年龄
     *
     * 不在担心对象为null
     */
    @Test
    public void test3() {
        final List<Employee> list = Lists.newArrayList(new Employee("emp1", 30),
                new Employee("emp2", 40),
                null,
                new Employee("emp4", 18));
        int sum = 0;
        for (Employee employee : list) {
            sum += Optional.fromNullable(employee).or(new Employee("empx", 0)).getAge();
        }
        System.out.println(sum);
    }
}
