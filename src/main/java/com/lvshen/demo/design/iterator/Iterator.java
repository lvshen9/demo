package com.lvshen.demo.design.iterator;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/10/21 21:35
 * @since JDK 1.8
 */
public interface Iterator {
    Object first();
    Object next();
    boolean hasNext();
}
