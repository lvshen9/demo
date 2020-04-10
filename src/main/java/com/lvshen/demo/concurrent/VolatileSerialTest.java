package com.lvshen.demo.concurrent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Description:指令重排
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/4/10 18:16
 * @since JDK 1.8
 */
public class VolatileSerialTest {

    static int x = 0, y = 0;

    public static void main(String[] args) throws InterruptedException {

        Set<String> resultSet = new HashSet<>();

        Map<String, Integer> resultMap = new HashMap<>();

        for (int i = 0; i < 1000000; i++) {
            x = 0;
            y = 0;
            resultMap.clear();

            Thread one = new Thread(() -> {
                int a = y;
                x = 1;
                resultMap.put("a", a);
            });

            Thread other = new Thread(() -> {
                int b = x;
                y = 1;
                resultMap.put("b", b);
            });

            one.start();
            other.start();

            one.join();
            other.join();

            /*if (resultMap.get("a") == 0 && resultMap.get("b") == 0) {
                resultSet.add("a=" + resultMap.get("a") + "," + "b=" + resultMap.get("b"));
                System.out.println(resultSet);
                break;
            }*/
            resultSet.add("a=" + resultMap.get("a") + "," + "b=" + resultMap.get("b"));
            System.out.println(resultSet);
        }
    }
}
