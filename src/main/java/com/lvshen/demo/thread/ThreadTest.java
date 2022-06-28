package com.lvshen.demo.thread;

import java.util.concurrent.Exchanger;

/**
 * Description: 线程之间的数据交换
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-6-22 15:02
 * @since JDK 1.8
 */
public class ThreadTest {
    public static void main(String[] args){
        test1();
    }
    private static void test1() {
        Exchanger exchanger = new Exchanger();

        new Thread(() -> {
            try {
                Object data = "-Lvshen的技术小屋AAA";
                System.out.println(Thread.currentThread().getName() + data);

                // 开始交换数据
                data = exchanger.exchange(data);
                System.out.println(Thread.currentThread().getName() + data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                Object data = "-Lvshen的技术小屋BBB";
                System.out.println(Thread.currentThread().getName() + data);

                // 开始交换数据
                data = exchanger.exchange(data);
                System.out.println(Thread.currentThread().getName() + data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                Object data = "-Lvshen的技术小屋CCC";
                System.out.println(Thread.currentThread().getName() + data);

                // 开始交换数据
                data = exchanger.exchange(data);
                System.out.println(Thread.currentThread().getName() + data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }


}
