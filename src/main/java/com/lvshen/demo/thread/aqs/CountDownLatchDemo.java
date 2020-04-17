package com.lvshen.demo.thread.aqs;

import java.util.concurrent.CountDownLatch;

/**
 * Description:倒计数器
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/4/16 14:28
 * @since JDK 1.8
 */
public class CountDownLatchDemo {
    static final int COUNT = 20;

    static CountDownLatch cdl = new CountDownLatch(COUNT);

    public static void main(String[] args) throws Exception {
        new Thread(new Teacher(cdl)).start();
        Thread.sleep(1);
        for (int i = 0; i < COUNT; i++) {
            new Thread(new Student(i, cdl)).start();
        }
        synchronized (CountDownLatchDemo.class) {
            CountDownLatchDemo.class.wait();
        }
    }

    static class Teacher implements Runnable {

        CountDownLatch cdl;

        Teacher(CountDownLatch cdl) {
            this.cdl = cdl;
        }

        @Override
        public void run() {
            System.out.println("老师发卷子。。。");
            try {
                cdl.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("老师收卷子。。。");
        }

    }

    static class Student implements Runnable {

        CountDownLatch cdl;
        int num;

        Student(int num, CountDownLatch cdl) {
            this.num = num;
            this.cdl = cdl;
        }

        @Override
        public void run() {
            System.out.println(String.format("学生(%s)写卷子。。。",num));
            //doingLongTime();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(String.format("学生(%s)写卷子。。。",num));
            cdl.countDown();
        }

    }
}
