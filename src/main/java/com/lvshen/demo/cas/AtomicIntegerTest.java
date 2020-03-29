package com.lvshen.demo.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/3/29 10:51
 * @since JDK 1.8
 */
public class AtomicIntegerTest {
    public static void main(String[] args) {
        AtomicInteger atomicInteger=new AtomicInteger();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
            if(!atomicInteger.compareAndSet(0,100)){
                System.out.println("0->100:失败");
            } else {
                System.out.println("0->100:成功");
            }
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(500);////注意这里睡了一会儿，目的是让第三个线程先执行判断的操作，从而让第三个线程修改失败
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
            if(!atomicInteger.compareAndSet(100,50)){
                System.out.println("100->50:失败");
            } else {
                System.out.println("100->50:成功");

            }
        }).start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName());

            if(!atomicInteger.compareAndSet(50,60)){
                System.out.println("50->60:失败");
            } else {
                System.out.println("50->60:成功");

            }
        }).start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
