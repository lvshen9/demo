package com.lvshen.demo.arithmetic.lru;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/7/27 21:22
 * @since JDK 1.8
 */
public class LinkedNode<K,V> {
    K k;
    V v;
    LinkedNode pre;
    LinkedNode next;

    LinkedNode(K k, V v) {
        this.k = k;
        this.v = v;
    }
}
