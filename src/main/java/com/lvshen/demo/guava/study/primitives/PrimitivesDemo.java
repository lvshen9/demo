package com.lvshen.demo.guava.study.primitives;

import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Bytes;
import com.google.common.primitives.Ints;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/6/24 11:34
 * @since JDK 1.8
 */
public class PrimitivesDemo {
	@Test
	public void testBytes() {
		ImmutableList<Integer> immutableList = ImmutableList.of(0, 2, 3, 4, 5, 6, 7, 9, 9);
		// List 转 字节数组
		byte[] b = Bytes.toArray(immutableList.asList());
		System.out.println(Arrays.toString(b));

		byte[] byteArray = { 1, 2, 3, 4, 5, 5, 7, 9, 9 };
		byte data = 5;
		System.out.println("5 is in list? " + Bytes.contains(byteArray, data));
		System.out.println("Index of 5: " + Bytes.indexOf(byteArray, data));
		System.out.println("Last index of 5: " + Bytes.lastIndexOf(byteArray, data));
	}

	@Test
	public void testInts() {
		int[] intArray = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

		// convert array of primitives to array of objects
		List<Integer> objectArray = Ints.asList(intArray);
		System.out.println(objectArray.toString());

		intArray = Ints.toArray(objectArray);
		System.out.print("[ ");
		for (int i = 0; i < intArray.length; i++) {
			System.out.print(intArray[i] + " ");
		}
		System.out.println("]");

		// check if element is present in the list of primitives or not
		System.out.println("5 is in list? " + Ints.contains(intArray, 5));

		// Returns the minimum
		System.out.println("Min: " + Ints.min(intArray));

		// Returns the maximum
		System.out.println("Max: " + Ints.max(intArray));

		int a = 1;
		int b = 2;
		System.out.println(Ints.compare(a, b));// -1 : a<b

		String joins = Ints.join(",", intArray);
		System.out.println(joins);
	}
}
