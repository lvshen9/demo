package com.lvshen.demo.cas;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/3/29 11:18
 * @since JDK 1.8
 */
public class MyAtomTest {
    public static void main(String[] args) {
        Thread[] threads = new Thread[20];
        MyAtomicInteger atomicInteger = new MyAtomicInteger();
        for (int i = 0; i < 20; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    atomicInteger.increment(1);
                }
            });
            threads[i].start();
        }
        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("x=" + atomicInteger.get());
    }
}
