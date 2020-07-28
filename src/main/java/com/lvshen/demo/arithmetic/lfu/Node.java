package com.lvshen.demo.arithmetic.lfu;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/7/28 22:11
 * @since JDK 1.8
 */
class Node {
    String key;
    String value;
    int frequency = 1;

    Node(String key, String value) {
        this.key = key;
        this.value = value;
    }
}

