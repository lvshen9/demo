package com.lvshen.demo;

import com.lvshen.demo.distributelock.ZkWatcherService;
import com.lvshen.demo.distributelock.order.OrderServiceImplWithDisLock;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/12/26 14:52
 * @since JDK 1.8
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class ZkWatcherTest {

    @Autowired
    private ZkWatcherService zkWatcher;

    @Autowired
    private OrderServiceImplWithDisLock orderService;

    @Test
    public void testWatcher() {
        String path = "/test/lvshen";
        zkWatcher.watcherNode(path);

        try {
            Thread.sleep(3000 * 60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("test is over");
    }

    @Test
    public void testCreateOrder() {
        orderService.createOrder();
        try {
            Thread.sleep(3000 * 60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("Test is over");
    }

    @Test
    public void testDisLock() {
        //并发数
        int currency = 20;

        //循环屏障
        CyclicBarrier cyclicBarrier = new CyclicBarrier(currency);

        for (int i = 0; i < currency; i++) {
            new Thread(() -> {
               // OrderServiceImplWithDisLock orderService = new OrderServiceImplWithDisLock();
                System.out.println(Thread.currentThread().getName() + "====start====");
                //等待一起出发
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
                orderService.createOrder();
            }).start();

        }
    }
}
