package com.lvshen.demo.design.decorator;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2020/1/20 13:50
 * @since JDK 1.8
 */
public class ConcreteDecorator extends Decorator {

	public ConcreteDecorator(Component component) {
		super(component);
	}

	@Override
	public void operation() {
		super.operation();
        addedFunction();
	}

	public void addedFunction() {
		System.out.println("为具体构件角色增加额外的功能addedFunction()");
	}
}
