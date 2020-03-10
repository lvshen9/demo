package com.lvshen.demo.guava.study.collections;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/6/24 8:55
 * @since JDK 1.8
 */
public class ImmutableDemoTest {

	@Test
	public void testJDKImmutable() {
		List<String> list = new ArrayList<>();
		list.add("a");
		list.add("b");
		list.add("c");

		List<String> unmodifiableList = Collections.unmodifiableList(list);
		System.out.println(unmodifiableList);

		list.add("ddd");
		System.out.println("往list添加一个元素:" + list);
		System.out.println("通过list添加元素之后的unmodifiableList:" + unmodifiableList);

		unmodifiableList.add("eee");
		System.out.println("往unmodifiableList添加一个元素:" + unmodifiableList);
	}

	@Test
	public void testGuavaImmutable() {
		List<String> list = new ArrayList<String>();
		list.add("a");
		list.add("b");
		list.add("c");

		ImmutableList<String> imlist = ImmutableList.copyOf(list);
		System.out.println("imlist：" + imlist);

		ImmutableList<String> imOflist = ImmutableList.of("peida", "jerry", "harry");
		System.out.println("imOflist：" + imOflist);

		ImmutableSortedSet<String> imSortList = ImmutableSortedSet.of("a", "b", "c", "a", "d", "b");
		System.out.println("imSortList：" + imSortList);

		list.add("baby");
		// 关键看这里是否imlist也添加新元素了
		System.out.println("list添加新元素之后看imlist:" + imlist);

		ImmutableSet<Color> imColorSet = ImmutableSet.<Color>builder().add(new Color(0, 255, 255))
				.add(new Color(0, 191, 255)).build();

		System.out.println("imColorSet:" + imColorSet);
	}

	@Test
	public void testCopyOf() {
        ImmutableSet<String> imSet = ImmutableSet.of("peida", "jerry", "harry", "lisa");
        System.out.println("imSet：" + imSet);

        ImmutableList<String> imlist = ImmutableList.copyOf(imSet);
        System.out.println("imlist：" + imlist);
        ImmutableSortedSet<String> imSortSet = ImmutableSortedSet.copyOf(imSet);
        System.out.println("imSortSet：" + imSortSet);

        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            list.add(i + "x");
        }
        System.out.println("list：" + list);
        ImmutableList<String> imInfolist = ImmutableList.copyOf(list.subList(2, 18));
        System.out.println("imInfolist：" + imInfolist);

        int imInfolistSize = imInfolist.size();
        System.out.println("imInfolistSize：" + imInfolistSize);

		ImmutableSet<String> imInfoSet = ImmutableSet.copyOf(imInfolist.subList(2, imInfolistSize - 3));
		System.out.println("imInfoSet：" + imInfoSet);

	}
}
