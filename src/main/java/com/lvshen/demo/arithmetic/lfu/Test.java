package com.lvshen.demo.arithmetic.lfu;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/7/28 20:54
 * @since JDK 1.8
 */
public class Test {
    @org.junit.Test
    public void test() {
        LFUMap<String, String> cache = new LFUMap<>();
        cache.put("a", "a");
        cache.put("b", "b");
        cache.put("c", "c");

        getMap(cache);

        cache.get("a");
        cache.get("a");

        //a(1) b(1) c(1)
        //b(1) c(1) a(3)
        //c(1) d(1) a(3)
        //c(1) d(2) a(3)
        //d(2) c(2) a(3)
        //e(1) c(2) a(3)
        getMap(cache);

        cache.put("d", "d");
        cache.get("d");
        cache.get("c");
        cache.put("e", "e");

        getMap(cache);

    }

    private void getMap(LFUMap<String, String> cache) {
        for (String key : cache.keySet()) {
            System.out.print(key+",");
        }
        System.out.println();
    }



}
