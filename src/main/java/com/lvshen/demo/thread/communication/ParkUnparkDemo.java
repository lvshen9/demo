package com.lvshen.demo.thread.communication;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/5/6 17:06
 * @since JDK 1.8
 */
public class ParkUnparkDemo {

    public static Object shop = null;

    public static void main(String[] args) throws InterruptedException {
        Thread consumerThread = new Thread(() -> {
            while (shop == null) {
                System.out.println("进入等待...");
                LockSupport.park();
            }
            System.out.println("成功进入商店shop");
        });
        consumerThread.start();

        //Thread.sleep(3000L);
        TimeUnit.SECONDS.sleep(2);
        shop = new Object();

        LockSupport.unpark(consumerThread);
        System.out.println("通知消费者");
    }
}
