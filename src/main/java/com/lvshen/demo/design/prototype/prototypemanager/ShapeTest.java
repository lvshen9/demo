package com.lvshen.demo.design.prototype.prototypemanager;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/12/30 16:48
 * @since JDK 1.8
 */
@Slf4j
public class ShapeTest {
	@Test
	public void test() {
		ProtoTypeManager protoTypeManager = new ProtoTypeManager();
		Shape circle = protoTypeManager.getShape("Circle");
		circle.countArea();

		Shape obj2 = protoTypeManager.getShape("Square");
		obj2.countArea();
	}
}
