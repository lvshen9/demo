package com.lvshen.demo.guava.study.collections;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Description: Map的可key&value反转
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/6/21 10:29
 * @since JDK 1.8
 */
public class BimapDemo {

	@Test
	public void testMap() {
		Map<Integer, String> logfileMap = Maps.newHashMap();
		logfileMap.put(1, "a.log");
		logfileMap.put(2, "b.log");
		logfileMap.put(3, "c.log");

		System.out.println("logfileMap:" + logfileMap);
		Map<String, Integer> integerMap = BimapDemo.reverseMap(logfileMap);
		System.out.println(integerMap);

	}

	@Test
	public void testMap1() {
		Map<Integer, String> logfileMap = Maps.newHashMap();
		logfileMap.put(1, "a.log");
		logfileMap.put(2, "b.log");
		logfileMap.put(3, "c.log");
		logfileMap.put(4, "c.log");
		System.out.println("logfileMap:" + logfileMap);

		Map<String, Integer> inverseMap = BimapDemo.reverseMap(logfileMap);
		System.out.println("reverseMap:" + inverseMap);
	}

	@Test
	public void testBimap() {
		HashBiMap<Integer, String> hashBiMap = HashBiMap.create();
		hashBiMap.put(1, "a.log");
		hashBiMap.put(2, "b.log");
		hashBiMap.put(3, "c.log");
		System.out.println("logfileMap:" + hashBiMap);
		// key-value反转
		BiMap<String, Integer> inverse = hashBiMap.inverse();
		System.out.println(inverse);
	}

	// 强制唯一性value
	@Test
	public void testBimap1() {
		BiMap<Integer, String> logfileMap = HashBiMap.create();
		logfileMap.put(1, "a.log");
		logfileMap.put(2, "b.log");
		logfileMap.put(3, "c.log");
		logfileMap.put(4, "c.log");
	}

	// forcePut覆盖原有值
	@Test
	public void testBimap2() {
		BiMap<Integer, String> logfileMap = HashBiMap.create();
		logfileMap.put(1, "a.log");
		logfileMap.put(2, "b.log");
		logfileMap.put(3, "c.log");

		logfileMap.forcePut(4, "c.log");

        System.out.println(logfileMap);

        BiMap<String, Integer> inverse = logfileMap.inverse();
        System.out.println(inverse);
    }

	/**
	 * 逆转Map的key和value
	 *
	 * [问题]： 1. 如何处理重复的value的情况。不考虑的话，反转的时候就会出现覆盖的情况.
	 *
	 * 2. 如果在反转的map中增加一个新的key，倒转前的map是否需要更新一个值呢?
	 */
	public static <S, T> Map<T, S> reverseMap(Map<S, T> map) {
		HashMap<T, S> inversetMap = new HashMap<>();
		for (Map.Entry<S, T> entry : map.entrySet()) {
			inversetMap.put(entry.getValue(), entry.getKey());
		}
		return inversetMap;
	}
}
