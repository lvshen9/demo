package com.lvshen.demo.lock;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/5/8 15:41
 * @since JDK 1.8
 */
public class LockDemo {
    static MyLock lock = new MyLock();

    public static void main(String[] args) throws InterruptedException {
        lock.lock();//主线程拿到锁
        new Thread(() -> {
            System.out.println("开始进入锁");
            lock.lock();
            System.out.println("拿到锁");
            lock.unlock();
            System.out.println("释放锁成功");
        }).start();

        Thread.sleep(3000L);
        lock.unlock();
    }
}
