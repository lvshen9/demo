package com.lvshen.demo.arithmetic.deque;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2021-11-16 10:05
 * @since JDK 1.8
 */
public class MyBlockQueue {
    private List queue = Lists.newArrayList();
    private int limit;

    /**
     * 队列中的元素总数
     */
    private AtomicInteger count = new AtomicInteger(0);

    /**
     * 显式锁
     */
    private final ReentrantLock putLock = new ReentrantLock();
    private final ReentrantLock takeLock = new ReentrantLock();

    /**
     * 锁对应的条件变量
     */
    private final Condition notFull = putLock.newCondition();
    private final Condition notEmpty = takeLock.newCondition();

    public MyBlockQueue(int limit) {
        this.limit = limit;
    }


    public void putQueue(Object item)
            throws InterruptedException {

        int cPut;
        putLock.lockInterruptibly();

        try {
            while (this.queue.size() == this.limit) {
                notFull.await();
            }

            this.queue.add(item);

            cPut = count.getAndIncrement();

            if (cPut + 1 < this.limit) {
                notFull.signal();
            }

        } finally {
            putLock.unlock();
        }

        if (cPut == 0) {
            signalNotEmpty();
        }
    }


    public Object takeQueue()
            throws InterruptedException {

        int cTake;
        Object o;
        takeLock.lockInterruptibly();
        try {
            while (this.queue.size() == 0) {
                notEmpty.await();
            }

            o = this.queue.remove(0);
            cTake = count.getAndDecrement();

            if (cTake - 1 > 0) {
                notEmpty.signal();
            }
        } finally {
            takeLock.unlock();
        }

        if (cTake == this.limit) {
            signalNotFull();
        }
        return o;

    }

    /**
     * 唤醒等待队列非空条件的线程
     */
    private void signalNotEmpty() {
        // 为了唤醒等待队列非空条件的线程，需要先获取对应的takeLock
        takeLock.lock();
        try {
            // 唤醒一个等待非空条件的线程
            notEmpty.signal();
        } finally {
            takeLock.unlock();
        }
    }

    /**
     * 唤醒等待队列未满条件的线程
     */
    private void signalNotFull() {
        // 为了唤醒等待队列未满条件的线程，需要先获取对应的putLock
        putLock.lock();
        try {
            // 唤醒一个等待队列未满条件的线程
            notFull.signal();
        } finally {
            putLock.unlock();
        }
    }

}
