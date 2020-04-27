package com.lvshen.demo.test;

//评测题目: 三个线程A、B、C，实现一个程序让线程A打印“A”，线程B打印“B”，线程C打印“C”，
//三个线程输出ABCABCABC......ABC，循环10次“ABC”
//30分钟


import java.util.concurrent.locks.LockSupport;

public class AliTest {

    static Thread t1, t2, t3;
    static volatile int count = 0;

    private static synchronized void addCount() {
        ++count;
    }

    public static void main(String[] args) throws InterruptedException {
        t1 = new Thread(() -> {

            while ((count < 30)) {
                if (count % 3 == 0) {
                    System.out.print("A");
                    addCount();
                    LockSupport.unpark(t2);
                    LockSupport.park();
                }
            }
        });
        t2 = new Thread(() -> {

            while ((count < 30)) {
                if (count % 3 == 1) {
                    System.out.print("B");
                    addCount();
                    LockSupport.unpark(t3);
                    LockSupport.park();
                }
            }
        });
        t3 = new Thread(() -> {

            while ((count < 30)) {
                if (count % 3 == 2) {
                    System.out.print("C ");
                    addCount();
                    LockSupport.unpark(t1);
                    LockSupport.park();
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();
    }

}
