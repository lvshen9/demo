package com.lvshen.demo.threadpool;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;


/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/3/13 14:23
 * @since JDK 1.8
 */
@Slf4j
public class FixedSizeThreadPoolTest {

    @Test
    public void testPool() {
        int taskSize = 6;
        FixedSizeThreadPool fixedSizeThreadPool = new FixedSizeThreadPool(3, taskSize);
        for (int i = 0; i < taskSize; i++) {
            fixedSizeThreadPool.execute(() -> {
               // int t = 0;
                System.out.println("任务被放入了仓库");
                //t++;
                try {
                    Thread.sleep(20000L);
                } catch (InterruptedException e) {
                    log.error("线程中断",e.getMessage());
                }
            });
        }

        fixedSizeThreadPool.shutDown();
    }

}