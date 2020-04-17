package com.lvshen.demo.threadlocal;

import java.text.SimpleDateFormat;
import java.util.Random;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/4/16 10:13
 * @since JDK 1.8
 */
public class ThreadLocalExample implements Runnable{

    private static final ThreadLocal<SimpleDateFormat> threadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyyMMdd HHmm"));

    @Override
    public void run() {
        System.out.println("Thread Name= " + Thread.currentThread().getName() + " default Formatter = " + threadLocal.get().toPattern());
        try {
            Thread.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadLocal.set(new SimpleDateFormat());

        System.out.println("Thread Name= " + Thread.currentThread().getName() + " Formatter = " + threadLocal.get().toPattern());
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadLocalExample threadLocalExample = new ThreadLocalExample();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(threadLocalExample, "" + i);
            Thread.sleep(new Random().nextInt(1000));
            thread.start();
        }
    }
}
