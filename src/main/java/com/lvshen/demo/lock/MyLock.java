package com.lvshen.demo.lock;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/5/8 15:20
 * @since JDK 1.8
 */
public class MyLock implements Lock {
    private AtomicInteger state = new AtomicInteger();
    Thread ownerThread = new Thread();
    LinkedBlockingQueue<Thread> waiters = new LinkedBlockingQueue<>();

    @Override
    public void lock() {
        if (!tryLock()) {
            //抢不到锁的线程放入阻塞队列中排队
            waiters.add(Thread.currentThread());
            while (true) {
                if (tryLock()) {
                    waiters.poll();//抢到锁，自己移出队列
                    return;
                } else {
                    //阻塞
                    LockSupport.park();
                }
            }
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        if (state.get() == 0) {
            if (state.compareAndSet(0, 1)) {
                ownerThread = Thread.currentThread();
                return true;
            }
            //可重入锁设计
        } else if (ownerThread == Thread.currentThread()) {
            state.set(state.get() + 1);
        }
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        if (ownerThread != Thread.currentThread()) {
            throw new RuntimeException("非法调用，当前线程并没有持有该锁");
        }
        if (state.decrementAndGet() == 0) {
            ownerThread = null;
            //通知其他等待抢锁的线程
            Thread waiterThread = waiters.peek();
            if (waiterThread != null) {
                LockSupport.unpark(waiterThread);
            }
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
