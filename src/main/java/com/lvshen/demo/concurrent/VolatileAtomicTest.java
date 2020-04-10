package com.lvshen.demo.concurrent;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/4/10 17:34
 * @since JDK 1.8
 */
public class VolatileAtomicTest {
    public static volatile int num = 0;

    public synchronized static void increase() {
        num++;
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    increase();
                }
            });

            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println(num);
    }
}
