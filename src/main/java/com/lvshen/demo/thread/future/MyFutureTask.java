package com.lvshen.demo.thread.future;

import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.LockSupport;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/4/21 12:32
 * @since JDK 1.8
 */
public class MyFutureTask<T> implements Runnable {

    Callable<T> callable;

    T result;

    volatile String state = "NEW";

    LinkedBlockingQueue<Thread> queue = new LinkedBlockingQueue<Thread>();

    public MyFutureTask(Callable<T> callable) {
        this.callable = callable;
    }

    public T get() {
        if ("END".equals(state)) {
            return result;
        }

        while (!"END".equals(state)) {
            queue.add(Thread.currentThread());
            LockSupport.park();
        }
        return result;
    }

    @Override
    public void run() {

        try {
            result = callable.call();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            state = "END";
        }

        Thread th = queue.poll();
        if (queue != null) {
            LockSupport.unpark(th);
            th = queue.poll();
        }
    }
}
