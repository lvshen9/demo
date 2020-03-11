package com.lvshen.demo.thread.communication;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2020/3/11 19:49
 * @since JDK 1.8
 */
public class WaitNotify {

    static int i = 0;
    static Object obj = new Object();
    static int limit = 20;
    
    public static void main(String[] args){
        new Thread(() -> {
           synchronized (obj) {
               while (i<limit) {
                   if (i % 3 == 0) {
                       System.out.println("t1:" + (++i));
                   }
                   obj.notifyAll();
                   try {
                       obj.wait();
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
               obj.notifyAll();
           }
        }).start();
        new Thread(() -> {
           synchronized (obj) {
               while (i < limit) {
                   if (i % 3 == 1) {
                       System.out.println("  t2:" + (++i));
                   }
                   obj.notifyAll();
                   try {
                       obj.wait();
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
               obj.notifyAll();
           }
        }).start();
        new Thread(() -> {
           synchronized (obj) {
               while (i < limit) {
                   if (i % 3 == 2) {
                       System.out.println("t3:" + (++i));
                   }
                   obj.notifyAll();
                   try {
                       obj.wait();
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
               obj.notifyAll();
           }
        }).start();
    }
}
