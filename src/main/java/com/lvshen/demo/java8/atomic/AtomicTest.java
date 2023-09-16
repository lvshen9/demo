package com.lvshen.demo.java8.atomic;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/10/31 11:56
 * @since JDK 1.8
 */
public class AtomicTest {

    public  static final  int MAX_THREAD_COUNT = 10;
    private static final int TASK_COUNT = 10;
    private static final int TARGET_COUNT = 100000000;

    private AtomicLong atomicLongVal = new AtomicLong(1);
    private LongAdder longAdderVal = new LongAdder();

    private static CountDownLatch latchForAtomicLong = new CountDownLatch(TASK_COUNT);
    private static CountDownLatch latchForLongAdder = new CountDownLatch(TASK_COUNT);


    @Test
    public void test() {
        int i = Runtime.getRuntime().availableProcessors();
        System.out.println(i);
    }

    public class AtomicLongThread implements Runnable {
        protected String name;
        protected long startTime;

        public AtomicLongThread(long startTime) {
            this.startTime = startTime;
        }

        @Override
        public void run()
        {
            long v = atomicLongVal.get();
            while(v < TARGET_COUNT) {
                v = atomicLongVal.incrementAndGet();
            }
            long endTime = System.currentTimeMillis();
            System.out.println("AtomicLongThread Spend:" + (endTime - startTime) + "ms, v = " + v);
            latchForAtomicLong.countDown();
        }

    }

    public class LongAddderThread implements Runnable {
        protected String name;
        protected long startTime;

        public LongAddderThread(long startTime) {
            this.startTime = startTime;
        }

        @Override
        public void run()
        {
            long v = longAdderVal.sum();
            while(v < TARGET_COUNT) {
                longAdderVal.increment();
                v = longAdderVal.sum();
            }
            long endTime = System.currentTimeMillis();
            System.out.println("LongAddderThread Spend:" + (endTime - startTime) + "ms, v = " + v);
            latchForLongAdder.countDown();
        }

    }

    public void testAtomicLongThread() throws InterruptedException {
        ExecutorService exe = Executors.newFixedThreadPool(MAX_THREAD_COUNT);
        long startTime = System.currentTimeMillis();
        AtomicLongThread atomicIntegerThread = new AtomicLongThread(startTime);
        for(int i = 0; i < TASK_COUNT; i++) {
            exe.submit(atomicIntegerThread);
        }
        latchForAtomicLong.await();
        exe.shutdown();
    }

    public void testLongAdderThread() throws InterruptedException {
        ExecutorService exe = Executors.newFixedThreadPool(MAX_THREAD_COUNT);
        long startTime = System.currentTimeMillis();
        LongAddderThread longAddderThread = new LongAddderThread(startTime);
        for(int i = 0; i < TASK_COUNT; i++) {
            exe.submit(longAddderThread);
        }
        latchForLongAdder.await();
        exe.shutdown();
    }

    /**
     * 下面的测试表明，AtomicLong的并发性能比LongAdder要差，在线程竞争越剧烈的场合，其表现出来的性能会更加优越
     */
    public static void main(String[] args) throws InterruptedException {

        /**
         * MAX_THREAD_COUNT、TASK_COUNT的值都为3时候测试结果如下：
         */
        AtomicTest test = new AtomicTest();
        test.testAtomicLongThread();
        test.testLongAdderThread();


        /**
         * MAX_THREAD_COUNT、TASK_COUNT的值都为10时候测试结果如下：
         */
        AtomicTest test1 = new AtomicTest();
        test1.testAtomicLongThread();
        test1.testLongAdderThread();
    }
}
