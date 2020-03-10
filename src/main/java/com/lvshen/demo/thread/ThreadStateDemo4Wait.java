package com.lvshen.demo.thread;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2020/3/10 18:12
 * @since JDK 1.8
 */
public class ThreadStateDemo4Wait {

    public static void main(String[] args) throws InterruptedException {

        Object object = new Object();
        Thread t1 = new Thread(() -> {
            synchronized (object) {

                try {
                    System.out.println("t1将wait(1000L)");
                    object.wait(1000L);
                    System.out.println("t1将wait");
                    object.wait();
                    System.out.println("t1将执行完");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });

        t1.start();
        Thread.sleep(1000L);

        synchronized (object) {

            System.out.println("t1的状态：" + t1.getState());
            object.notify();
            Thread.sleep(1000L);
            System.out.println("t1的状态：" + t1.getState());
        }

        Thread.sleep(3000L);
        System.out.println("t1的状态：" + t1.getState());

        Thread.sleep(1000L);

        synchronized (object) {
            object.notify();
        }
        System.out.println("t1的状态：" + t1.getState());
        Thread.sleep(1000L);
        System.out.println("t1的状态：" + t1.getState());

    }
}
