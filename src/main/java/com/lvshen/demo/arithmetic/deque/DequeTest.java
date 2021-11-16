package com.lvshen.demo.arithmetic.deque;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/12/20 9:38
 * @since JDK 1.8
 */
@Slf4j
public class DequeTest {

    @Test
    public void test1() {
        Deque<String> arrayDeque = new ArrayDeque<>();
        //arrayDeque.addLast("t1");
        arrayDeque.addFirst("t2");
        arrayDeque.addFirst("t3");
        arrayDeque.addFirst("t4");
        arrayDeque.addFirst("t5");
        //arrayDeque.addLast("t6");

        log.info(arrayDeque.toString());
        //String pop = arrayDeque.pop();
        String removeLast = arrayDeque.removeLast();
        log.info(arrayDeque.toString());
        log.info(removeLast);
    }

    @Test
    public void test() {
        String str = "123";
        String str2 = new String("123");
        String str3 = str2.intern();
        String str4 = str.intern();

        System.out.println(str == str2);
        System.out.println(str == str3);
        System.out.println(str2 == str3);

        System.out.println("-------------------------");
        System.out.println(str == str4);
        System.out.println(str2 == str4);
        System.out.println(str3 == str4);


    }

    @Test
    public void testBlock() throws InterruptedException {
        // 创建2个线程
        int threads = 2;
        // 每个线程执行10次
        int times = 10;
        MyBlockQueue myBlockQueue = new MyBlockQueue(2);

        List<Thread> threadList = new ArrayList<>(threads * 2);

        long startTime = System.currentTimeMillis();
        // 创建2个生产者线程，向队列中并发放入数字0到19，每个线程放入10个数字
        for (int i = 0; i < threads; ++i) {
            final int offset = i * times;
            Thread producer = new Thread(() -> {
                try {
                    for (int j = 0; j < times; ++j) {
                        myBlockQueue.putQueue(new Integer(offset + j));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            threadList.add(producer);
            producer.start();
        }

        // 创建2个消费者线程，从队列中弹出20次数字并打印弹出的数字
        for (int i = 0; i < threads; ++i) {
            Thread consumer = new Thread(() -> {
                try {
                    for (int j = 0; j < times; ++j) {
                        Integer element = (Integer) myBlockQueue.takeQueue();
                        System.out.println(Thread.currentThread().getName() + "：" + element);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            threadList.add(consumer);
            consumer.start();
        }

        // 等待所有线程执行完成
        for (Thread thread : threadList) {
            thread.join();
        }

        // 打印运行耗时
        long endTime = System.currentTimeMillis();
        System.out.println(String.format("总耗时：%.2fs", (endTime - startTime) / 1e3));
    }
}


