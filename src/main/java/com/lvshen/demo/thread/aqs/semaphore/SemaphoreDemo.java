package com.lvshen.demo.thread.aqs.semaphore;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Description:信号量机制
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/3/21 20:34
 * @since JDK 1.8
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        SemaphoreDemo semaphoreDemo = new SemaphoreDemo();
        int count = 9;    //数量

        //循环屏障
        CyclicBarrier cyclicBarrier = new CyclicBarrier(count);

        Semaphore semaphore = new Semaphore(5);//限制请求数量
        for (int i = 0; i < count; i++) {
            String vipNo = "vip-00" + i;
            new Thread(() -> {
                try {
                    cyclicBarrier.await();
                    //semaphore.acquire();//获取令牌
                    boolean tryAcquire = semaphore.tryAcquire(3000L, TimeUnit.MILLISECONDS);
                    if (!tryAcquire) {
                        System.out.println("获取令牌失败：" + vipNo);
                    }

                    //执行操作逻辑
                    System.out.println("当前线程：" + Thread.currentThread().getName());
                    semaphoreDemo.service(vipNo);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            }).start();
        }

    }

    // 限流控制5个线程同时访问
    public void service(String vipNo) throws InterruptedException {
        System.out.println("楼上出来迎接贵宾一位，贵宾编号" + vipNo + ",...");
        Thread.sleep(new Random().nextInt(3000));
        System.out.println("欢送贵宾出门，贵宾编号" + vipNo);
    }

}
