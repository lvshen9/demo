package com.lvshen.demo.threadpool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/3/13 12:20
 * @since JDK 1.8
 */
public class RunnableDemo {
    public static class RunableTest implements Runnable {

        @Override
        public void run() {
            System.out.println("Runnable运行线程：" + Thread.currentThread().getName());

        }

    }

    public static class ThreadDemo extends Thread {
        @Override
        public void run() {
            System.out.println("Thread运行线程："+ Thread.currentThread().getName());
        }

    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        RunableTest runableTest = new RunableTest();
        ThreadDemo threadDemo = new ThreadDemo();

        Thread thread = new Thread(runableTest);

        // runableTest.run();
        thread.start();
        threadDemo.start();

        System.out.println("main方法里运行的线程："+ Thread.currentThread().getName());

        //callable使用
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
                int result = threadLocalRandom.nextInt(0,10);
                System.out.println("call方法中的线程："+ Thread.currentThread().getName());
                return Integer.toString(result);
            }
        };

        FutureTask<String> futureTask = new FutureTask<>(callable);
        new Thread(futureTask).start();
        System.out.println(futureTask.get());
    }
}
