package com.lvshen.demo.design.iterator;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/10/21 21:34
 * @since JDK 1.8
 */
public interface Aggregate {
    void add(Object obj);
    void remove(Object obj);
    Iterator getIterator();
}
