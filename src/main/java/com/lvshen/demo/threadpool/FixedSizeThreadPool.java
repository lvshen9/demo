package com.lvshen.demo.threadpool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/3/13 13:57
 * @since JDK 1.8
 */
public class FixedSizeThreadPool {

    //自定义线程池
    //1. 多个线程组成
    private List<Thread> workers;

    //2.仓库
    private BlockingQueue<Runnable> queue;

    private volatile boolean isWorking = true;

    public FixedSizeThreadPool(int poolSize, int taskSize) {

        if (poolSize <= 0 || taskSize <= 0) {
            throw new IllegalArgumentException("非法参数");
        }

        this.queue = new LinkedBlockingQueue<>(taskSize);
        this.workers = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < poolSize; i++) {
            Worker worker = new Worker(this);
            worker.start();
            workers.add(worker);
        }
    }

    //3.具体线程
    public static class Worker extends Thread {
        private FixedSizeThreadPool pool;

        public Worker(FixedSizeThreadPool pool) {
            this.pool = pool;
        }

        @Override
        public void run() {
            while (pool.isWorking || pool.queue.size() > 0) {
                Runnable task = null;

                try {
                    if (pool.isWorking) {
                        task = this.pool.queue.take();//阻塞方式拿，拿不到会一直等待
                    } else {
                        task = this.pool.queue.poll();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (task != null) {
                    task.run();
                    System.out.println("线程：" + Thread.currentThread().getName() + "执行完毕");

                }
            }
        }
    }

    //向仓库库中放入任务
    public void submit(Runnable runnable) throws InterruptedException {
        this.queue.put(runnable);
    }

    public boolean execute(Runnable runnable) {
        if (isWorking) {
            return this.queue.offer(runnable);
        }

        return false;
    }

    public void shutDown() {
        this.isWorking = false;

        for(Thread thread : workers){
           if (Thread.State.BLOCKED.equals(thread.getState())) {
               thread.interrupt();//中断线程
           }
        }
    }


}
