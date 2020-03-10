package com.lvshen.demo.design.prototype;

/**
 * Description:原型模式
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/12/30 13:45
 * @since JDK 1.8
 */
public class Realizetype implements Cloneable {
	public Realizetype() {
		System.out.println("具体原型创建成功！");
	}
	@Override
    public Object clone() throws CloneNotSupportedException {
        System.out.println("具体原型复制成功！");
        return super.clone();
    }
}
