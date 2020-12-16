package com.lvshen.demo.random;

import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-12-7 9:05
 * @since JDK 1.8
 */
public class RandomTest {

    @Test
    public void testRandom() {
        for (int i = 0; i < 10; i++) {
            double random = Math.random();
            System.out.println(random);
        }
    }

    public static void main(String[] args){
        new RandomTest().testThreadLocalRandom();
    }
    //@Test
    public void testThreadLocalRandom() {
        for (int i = 0; i < 3 ; i++) {
            new Thread(() -> System.out
                    .println(Thread.currentThread().getName() + ": " +
                            ThreadLocalRandom.current().nextDouble())).start();
        }
    }
}
