package com.lvshen.demo.queue.delayqueue;

import java.util.Date;
import java.util.concurrent.SynchronousQueue;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-12-2 15:50
 * @since JDK 1.8
 */
public class Test {
    public static void main(String[] args) {
        SynchronousQueue queue = new SynchronousQueue();

        // 入队
        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                try {
                    System.out.println(new Date() + "，元素入队");
                    queue.put("Data " + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();

        // 出队
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    System.out.println(new Date() + "，元素出队：" + queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
