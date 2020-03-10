package com.lvshen.demo.thread;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2020/3/10 18:12
 * @since JDK 1.8
 */
public class ThreadStateDemo3Synchronized {

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            synchronized (ThreadStateDemo3Synchronized.class) {
                System.out.println("t1抢到锁");
            }
        });

        synchronized (ThreadStateDemo3Synchronized.class) {
            t1.start();
            Thread.sleep(1000L);
            System.out.println("t1抢不到锁的状态："+ t1.getState());
        }
    }
}
