package com.lvshen.demo.integer;

import org.junit.Test;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/8/1 15:59
 * @since JDK 1.8
 */
public class IntegerTest {

	public static void main(String[] args) {
		// -128--127之间

		Integer i1 = 100;
		Integer i2 = 100;
		if (i1 == i2) {
			System.out.println("i1 == i2");
		} else {
			System.out.println("i1 != i2 ");
		}

		if (i1 < i2) {

        }

		// 大于127
		Integer i3 = 200;
		Integer i4 = 200;
		/*if (i3 == i4) {
			System.out.println("i3 == i4");
		} else {
			System.out.println("i3 != i4 ");   (√)}*/

		/*if (i3.compareTo(i4) == 0) {
            System.out.println("i3 == i4");    (√)
		} else {
            System.out.println("i3 != i4 ");
        }*/

        if (i3.intValue() == i4.intValue()) {
            System.out.println("i3 == i4");
        } else {
            System.out.println("i3 != i4 ");
        }

	}

	@Test
	public void testInteger() {
		String str = "12";
		int parseInt = Integer.parseInt(str);
		Integer valueOf = Integer.valueOf(str);
		System.out.println("parseInt" + parseInt);
		System.out.println("valueOf" + valueOf);
	}
}
