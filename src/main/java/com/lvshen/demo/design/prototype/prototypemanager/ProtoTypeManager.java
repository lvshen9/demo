package com.lvshen.demo.design.prototype.prototypemanager;

import java.util.HashMap;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/12/30 16:47
 * @since JDK 1.8
 */
public class ProtoTypeManager {
	private HashMap<String, Shape> ht = new HashMap<>();

	public ProtoTypeManager() {
		ht.put("Circle", new Circle());
		ht.put("Square", new Square());
	}

	public void addshape(String key, Shape obj) {
		ht.put(key, obj);
	}

	public Shape getShape(String key) {
		Shape temp = ht.get(key);
		return (Shape) temp.clone();
	}
}
