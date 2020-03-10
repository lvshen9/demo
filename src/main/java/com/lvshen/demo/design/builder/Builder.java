package com.lvshen.demo.design.builder;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/12/31 9:28
 * @since JDK 1.8
 */
public abstract class Builder {
	protected Product product = new Product();

	public abstract void buildPartA();

	public abstract void buildPartB();

	public abstract void buildPartC();

	public Product getResult() {
		return product;
	}
}
