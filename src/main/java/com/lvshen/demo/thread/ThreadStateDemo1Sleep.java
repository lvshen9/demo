package com.lvshen.demo.thread;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2020/3/10 18:12
 * @since JDK 1.8
 */
public class ThreadStateDemo1Sleep {
    static volatile boolean running = true;

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
           try {
               while (running) {
                   System.out.println("t1 running is false,t1将sleep");
                   Thread.sleep(3000L);
               }
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
        });

        System.out.println("new t1 t1 的状态：" + t1.getState()); // new

        t1.start();

        Thread.sleep(2000L);

        running = false;

        Thread.sleep(2000L);

        System.out.println("t1.sleep()时的状态：" + t1.getState()); // timed_waiting
    }
}
