package com.lvshen.demo.guava.study.collections;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import org.junit.Test;

import java.util.Iterator;
import java.util.Set;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/6/24 10:40
 * @since JDK 1.8
 */
public class MultisetDemo {
	@Test
	public void test() {
		Multiset<String> multiset = HashMultiset.create();
		multiset.add("a");
		multiset.add("b");
		multiset.add("c");
		multiset.add("d");
		multiset.add("a");
		multiset.add("b");
		multiset.add("c");
		multiset.add("b");
		multiset.add("b");
		multiset.add("b");

		System.out.println("元素b出现的次数 : " + multiset.count("b"));
		System.out.println("元素个数 : " + multiset.size());

		// 转成Set，去重遍历集合
		Set<String> set = multiset.elementSet();
		System.out.println("Set [");
		for (String s : set) {
			System.out.println(s);
		}
		System.out.println("]");

		// 使用迭代器遍历集合
		Iterator<String> iterator = multiset.iterator();
		System.out.println("MultiSet [");
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
		System.out.println("]");

		System.out.println("MultiSet [");
		for (Multiset.Entry<String> entry : multiset.entrySet()) {
			System.out.println("元素: " + entry.getElement() + ", 个数: " + entry.getCount());
		}
		System.out.println("]");

		// 移除2个b元素
		multiset.remove("b", 2);
		System.out.println("移除2个b元素后，b元素个数为 : " + multiset.count("b"));

		boolean b = multiset.contains("a");
		System.out.println(b);
	}

	@Test
	public void test2() {
        HashMultiset<Integer> integerHashMultiset = HashMultiset.create();
        integerHashMultiset.add(1);
        System.out.println(integerHashMultiset);
        
		integerHashMultiset.add(2, 3);
        System.out.println(integerHashMultiset);

        System.out.println(integerHashMultiset.count(2));
    }
}
