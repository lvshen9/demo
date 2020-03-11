package com.lvshen.demo.thread.communication;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2020/3/11 21:47
 * @since JDK 1.8
 */
public class CasDemo {
    static volatile int t = 1;
    static volatile int i = 0;

    public static void main(String[] args){

        new Thread(() -> {
           while (i < 10) {
               while (t != 1) {

               }
               System.out.println("t1:" + (++i));
               t = 2;
           }
        }).start();
        new Thread(() -> {
           while (i < 10) {
               while (t != 2) {

               }
               System.out.println(" t2:" + (++i));
               t = 1;
           }
        }).start();

    }
}
