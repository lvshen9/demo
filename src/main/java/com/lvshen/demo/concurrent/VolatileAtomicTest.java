package com.lvshen.demo.concurrent;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/4/10 17:34
 * @since JDK 1.8
 */
public class VolatileAtomicTest {
    public static int num = 0;

    static Lock lock = new ReentrantLock();

    AtomicInteger atomicInteger = new AtomicInteger();

    public static void increase() {
        try {
            lock.lock();
            num++;
        } finally {
            lock.unlock();
        }
    }  //这里加了锁 上面的num可以不用加volatile,寄存器获取数据直接从主内存中获取。

    @Test
    public void test() throws InterruptedException {
        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    num = atomicInteger.incrementAndGet();
                }
            });

            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println(num);
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
