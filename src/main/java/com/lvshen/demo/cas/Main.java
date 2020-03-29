package com.lvshen.demo.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/3/29 10:28
 * @since JDK 1.8
 */
public class Main {
    public static void main(String[] args) {
        Thread[] threads = new Thread[20];
        AtomicInteger atomicInteger = new AtomicInteger();
        for (int i = 0; i < 20; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    atomicInteger.incrementAndGet();
                }
            });
            threads[i].start();
        }
        join(threads);
        System.out.println("x=" + atomicInteger.get());
    }

    private static void join(Thread[] threads) {
        for (int i = 0; i < 20; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
