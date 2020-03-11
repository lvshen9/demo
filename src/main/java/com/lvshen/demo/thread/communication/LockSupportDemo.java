package com.lvshen.demo.thread.communication;

import java.util.concurrent.locks.LockSupport;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2020/3/11 21:24
 * @since JDK 1.8
 */
public class LockSupportDemo {
    static int i = 0;
    static Thread t1, t2, t3;

    public static void main(String[] args) {
        t1 = new Thread(() -> {
            while (i < 10) {
                System.out.println("t1:" + (++i));
                LockSupport.unpark(t2);
                LockSupport.park();
            }
        });
        t2 = new Thread(() -> {
            while (i < 10) {
                System.out.println("   t2:" + (++i));
                LockSupport.unpark(t3);
                LockSupport.park();
            }
        });
        t3 = new Thread(() -> {
            while (i < 10) {
                System.out.println("t3:" + (++i));
                LockSupport.unpark(t1);
                LockSupport.park();
            }
        });

        t1.start();
        t2.start();
        t3.start();

    }
}
