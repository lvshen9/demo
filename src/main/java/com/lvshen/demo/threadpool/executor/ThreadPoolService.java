package com.lvshen.demo.threadpool.executor;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-9-14 15:33
 * @since JDK 1.8
 */
public class ThreadPoolService {
    static ExecutorService threadPool = new ThreadPoolExecutor(5,10,0L, TimeUnit.MILLISECONDS,new LinkedBlockingDeque<>());

    @Test
    public void testThreadPool() throws InterruptedException {
        List<Integer> list = Lists.newArrayList();
        threadPool.submit(() -> {
            for (int i = 0; i < 1000; i++) {
                list.add(i);
            }
        });
        Thread.sleep(5000L);
        System.out.println(list);
    }
}
