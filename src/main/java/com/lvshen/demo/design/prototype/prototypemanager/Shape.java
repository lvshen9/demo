package com.lvshen.demo.design.prototype.prototypemanager;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/12/30 14:16
 * @since JDK 1.8
 */
public interface Shape extends Cloneable {
    public Object clone();
    public void countArea();
}
