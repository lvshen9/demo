package com.lvshen.demo.arithmetic.lru;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/7/27 21:35
 * @since JDK 1.8
 */
public class Test {
    public static void main(String[] args) {
        LRUCache lruCache = new LRUCache(4);
        lruCache.put("A", 1);
        lruCache.put("B", 2);
        lruCache.put("C", 3);
        //lruCache.getMap();

        Object b = lruCache.get("B");
        System.out.println(b);
        //lruCache.getMap();
        System.out.println("1");


    }

    @org.junit.Test
    public void test() {
        LRULinkedHashMap<String, Integer> map = new LRULinkedHashMap<>(4);
        map.put("A", 1);
        map.put("B", 2);
        map.put("C", 3);

        System.out.println(map);
        map.get("B");
        System.out.println(map);
        map.put("D",4);
        map.put("E",5);
        System.out.println(map);
    }
}
