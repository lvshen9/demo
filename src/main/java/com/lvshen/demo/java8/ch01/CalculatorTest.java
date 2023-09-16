package com.lvshen.demo.java8.ch01;

import org.junit.Test;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/4/10 19:32
 * @since JDK 1.8
 */
public class CalculatorTest {

	@Test
	public void testCalculator() {
		int a = 1;
		int b = 2;

		Calculator calculator = CalculatorFactory.getInstance();

		int add = calculator.add(a, b);
		System.out.println("add: " + add);

		int divide = calculator.divide(a, b);
		System.out.println("divide: " + divide);

		int multiply = calculator.multiply(a, b);
		System.out.println("multiply: " + multiply);

		int subtract = calculator.subtract(a, b);
		System.out.println("subtract: " + subtract);

		int remainder = calculator.remainder(add, divide + 1);
        System.out.println("remainder: " + remainder);

	}
}
