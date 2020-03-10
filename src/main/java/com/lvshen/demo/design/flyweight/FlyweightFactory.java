package com.lvshen.demo.design.flyweight;

import java.util.HashMap;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2020/1/20 15:04
 * @since JDK 1.8
 */
public class FlyweightFactory {
	private HashMap<String, Flyweight> flyweights = new HashMap<>();

	public Flyweight getFlyweight(String key) {
		Flyweight flyweight = (Flyweight) flyweights.get(key);
		if (flyweight != null) {
			System.out.println("具体享元" + key + "已经存在，被成功获取！");
		} else {
			flyweight = new ConcreteFlyweight(key);
			flyweights.put(key, flyweight);
		}
		return flyweight;
	}
}
