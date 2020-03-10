package com.lvshen.demo.design.flyweight;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2020/1/20 15:01
 * @since JDK 1.8
 */
public class ConcreteFlyweight implements Flyweight {
	private String key;

	ConcreteFlyweight(String key) {
		this.key = key;
		System.out.println("具体享元" + key + "被创建！");
	}

	@Override
	public void operation(UnsharedConcreteFlyweight state) {
		System.out.print("具体享元" + key + "被调用，");
		System.out.println("非享元信息是:" + state.getInfo());
	}
}
