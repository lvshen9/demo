package com.lvshen.demo.thread.aqs;

import lombok.SneakyThrows;

import java.util.concurrent.CyclicBarrier;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/4/16 14:38
 * @since JDK 1.8
 */
public class CyclicBarrierDemo {
    static final int COUNT = 5;

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < COUNT; i++) {
            new Thread(new Staff(i, cb)).start();
        }
        synchronized (CyclicBarrierDemo.class) {
            CyclicBarrierDemo.class.wait();
        }
    }

    static CyclicBarrier cb = new CyclicBarrier(COUNT, new Singer());

    static class Singer implements Runnable {

        @Override
        public void run() {
            System.out.println("为大家唱歌。。。");
        }

    }

    static class Staff implements Runnable {

        CyclicBarrier cb;
        int num;

        Staff(int num, CyclicBarrier cb) {
            this.num = num;
            this.cb = cb;
        }

        @SneakyThrows
        @Override
        public void run() {
            System.out.println(String.format("员工(%s)出发。。。", num));
            Thread.sleep(2000);
            System.out.println(String.format("员工(%s)到达地点一。。。", num));
            try {
                cb.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(String.format("员工(%s)再出发。。。", num));
            Thread.sleep(2000);
            //doingLongTime();
            System.out.println(String.format("员工(%s)到达地点二。。。", num));
            try {
                cb.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(String.format("员工(%s)再出发。。。", num));
            Thread.sleep(2000);
            //doingLongTime();
            System.out.println(String.format("员工(%s)到达地点三。。。", num));
            try {
                cb.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(String.format("员工(%s)结束。。。", num));
        }

    }
}
