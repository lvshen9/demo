package com.lvshen.demo.cas;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/3/29 11:28
 * @since JDK 1.8
 */
public class AtomicStampedTest {
    public static void main(String[] args) {
        try {
            AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(150, 0);
            Thread thread1 = new Thread(() -> {
                Integer oldValue = atomicStampedReference.getReference();
                int stamp = atomicStampedReference.getStamp();
                if (atomicStampedReference.compareAndSet(oldValue, 50, 0, stamp + 1)) {
                    System.out.println("150->50 成功:" + (stamp + 1));
                }
            });
            thread1.start();

            Thread thread2 = new Thread(() -> {
                try {
                    Thread.sleep(1000);//睡一会儿，是为了保证线程1 执行完毕
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Integer oldValue = atomicStampedReference.getReference();
                int stamp = atomicStampedReference.getStamp();
                if (atomicStampedReference.compareAndSet(oldValue, 150, stamp, stamp + 1)) {
                    System.out.println("50->150 成功:" + (stamp + 1));
                }
            });
            thread2.start();

            Thread thread3 = new Thread(() -> {
                try {
                    Thread.sleep(2000);//睡一会儿，是为了保证线程1，线程2 执行完毕
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Integer oldValue = atomicStampedReference.getReference();
                int stamp = atomicStampedReference.getStamp();
                if (atomicStampedReference.compareAndSet(oldValue, 90, 0, stamp + 1)) {
                    System.out.println("150->90 成功:" + (stamp + 1));
                }
            });
            thread3.start();

            thread1.join();
            thread2.join();
            thread3.join();
            System.out.println("现在的值是" + atomicStampedReference.getReference() + ";stamp是" + atomicStampedReference.getStamp());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testThread() {
        Thread thread1 = new Thread(() -> funA(),"Thread-1");
        Thread thread2 = new Thread(() -> funB(),"Thread-2");
        Thread thread3 = new Thread(() -> funC(),"Thread-3");

        thread1.start();
        thread2.start();
        thread3.start();
    }

    public void funA() {
        System.out.println("A1");
        System.out.println("A2");
        System.out.println("A3");
    }

    public void funB() {
        System.out.println("B1");
        System.out.println("B2");
        System.out.println("B3");
    }

    public void funC() {
        System.out.println("C1");
        System.out.println("C2");
        System.out.println("C3");
    }
}
