package com.lvshen.demo.thread;

import java.util.concurrent.locks.LockSupport;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2020/3/10 18:12
 * @since JDK 1.8
 */
public class ThreadStateDemo5Park {

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            LockSupport.park();
            System.out.println("t1将执行完毕!!!");
        });

        t1.start();

        Thread.sleep(1000L);

        System.out.println("t1 park后的状态：" + t1.getState());
        LockSupport.unpark(t1);
        System.out.println("t1 unpark后的状态：" + t1.getState());

    }
}
