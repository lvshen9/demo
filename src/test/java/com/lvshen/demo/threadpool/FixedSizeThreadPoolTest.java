package com.lvshen.demo.threadpool;

import org.junit.Test;


/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/3/13 14:23
 * @since JDK 1.8
 */
public class FixedSizeThreadPoolTest {

    @Test
    public  void testPool() {
        FixedSizeThreadPool fixedSizeThreadPool = new FixedSizeThreadPool(3, 6);
        for (int i = 0; i < 6; i++) {
            fixedSizeThreadPool.execute(() -> {
                System.out.println("任务被放入了仓库");
                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    System.out.println("线程中断");
                }
            });
        }
        fixedSizeThreadPool.shutDown();
    }

}