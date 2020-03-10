package com.lvshen.demo.javafoundation;

import org.junit.Test;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/7/3 17:58
 * @since JDK 1.8
 */
public class SwapTest {

	/**
	 * 基本数据类型：值传递
	 */
	@Test
	public void test() {
		int x = 10;
		int y = 20;
		swap(x, y);

		System.out.println("x[2] = " + x);
		System.out.println("y[2] = " + y);
	}

	/**
	 * 引用数据类型：地址传递（数组）
	 */
	@Test
	public void test2() {
		int[] a = { 10, 20 };
		System.out.println("a[0] :" + a[0] + ", a[1] : " + a[1]);
		swap(a, 0, 1);
		System.out.println("a[0] :" + a[0] + ", a[1] : " + a[1]);
	}

	@Test
	public void test3() {
		Foo f = new Foo("f");
		changeReference(f);
		modifyReference(f);
		System.out.println("test3: " + f.toString());
	}

	@Test
	public void test4() {
		String str = new String("hello");
		char[] chars = { 'w', 'o', 'r', 'l', 'd' };
		change(str, chars);
		System.out.println(str + " " + new String(chars));
	}

	@Test
	public void test5() {
		StringBuffer sb = new StringBuffer("hello");
		change(sb);
		System.out.println(sb);
	}

	@Test
	public void test6() {
		StringBuffer a = new StringBuffer("A");
		StringBuffer b = new StringBuffer("B");
		operate(a, b);
		System.out.println(a + "," + b);

	}

	private static void swap(int x, int y) {
		int temp = x;
		x = y;
		y = temp;

		System.out.println("x[1] = " + x);
		System.out.println("y[1] = " + y);
	}

	private static void swap(int[] a, int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
		System.out.println("a[0] :" + a[0] + ", a[1] : " + a[1]);
	}

	private static void changeReference(Foo a) {
		Foo b = new Foo("b");
		a = b;
		System.out.println("changeReference: " + b.toString());
        System.out.println("a: " + a.toString());
	}

	private static void modifyReference(Foo c) {
		c.setAttribute("c");
		System.out.println("modifyReference: " + c.toString());
	}

	private static void change(StringBuffer sb) {
		sb.append("world");
	}

	private static void change(String str, char[] chs) {
		str.replace('h', 'H');
		chs[0] = 'W';
	}

	private static void operate(StringBuffer x, StringBuffer y) {
		x.append(y);
		y = x;
	}

	static class Foo {
		String attribute;

		public String getAttribute() {
			return attribute;
		}

		public void setAttribute(String attribute) {
			this.attribute = attribute;
		}

		public Foo() {
		}

		public Foo(String attribute) {
			this.attribute = attribute;
		}

		@Override
		public String toString() {
			return "Foo{" + "attribute='" + attribute + '\'' + '}';
		}
	}
}
