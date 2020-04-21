package com.lvshen.demo.distributelock.order;

import com.lvshen.demo.distributelock.ZkDistributeLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/3/15 23:48
 * @since JDK 1.8
 */
@Component
@Slf4j
public class OrderServiceImplWithDisLock implements OrderService {

    /*@Autowired
    private ZkDistributeLock zkDistributeLock;*/

    private OrderCodedGenerator ocg = new OrderCodedGenerator();

    //private Lock lock = new ReentrantLock();
    @Override
    public void createOrder() {

        String lockPath = "/distribute_lock";
        String orderCode = null;
        //zk 分布式锁
        Lock lock = new ZkDistributeLock(lockPath);
        // zkDistributeLock.setLockPath(lockPath);
        lock.lock();
        try {
            orderCode = ocg.getOrderCode();
        } finally {
            lock.unlock();
        }

        log.info("当前线程：{}，生成订单编号：{}",Thread.currentThread().getName() , orderCode);
        System.out.println(String.format("当前线程：{%s}，生成订单编号：{%s}",Thread.currentThread().getName() , orderCode));
        //其他逻辑

    }
}
