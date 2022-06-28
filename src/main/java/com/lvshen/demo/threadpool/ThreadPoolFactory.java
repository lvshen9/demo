package com.lvshen.demo.threadpool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-6-28 8:13
 * @since JDK 1.8
 */
@Slf4j
public class ThreadPoolFactory {
    private static final int CORE_POOL_SIZE = (Runtime.getRuntime().availableProcessors() == 1 ? 5 :
            2 * Runtime.getRuntime().availableProcessors());
    private static final int MAX_POOL_SIZE = (CORE_POOL_SIZE == 16 ? 2 * CORE_POOL_SIZE : 16);
    private static final int QUEUE_CAPACITY = 1000;

    private static final String THREAD_NAME_PREFIX = "SRM-Thread-Pool-";
    private static final Long KEEP_ALIVE_TIME = 1L;
    private static final int QUEUE_TIME_OUT = 60;
    /**
     * 创建线程池
     *
     * @param taskNamePrefix
     * @return
     */
    protected ThreadPoolTaskExecutor buildTaskExecutor(String taskNamePrefix) {
        log.info("buildTaskExecutor,初始化线程池：{}-CORE_POOL_SIZE={}", taskNamePrefix,
                CORE_POOL_SIZE);
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setQueueCapacity(QUEUE_CAPACITY);
        executor.setThreadNamePrefix(taskNamePrefix);
        executor.setKeepAliveSeconds(KEEP_ALIVE_TIME.intValue());
        //executor.setThreadFactory(SPRING_THREAD_FACTORY);
        executor.setRejectedExecutionHandler((r, e) -> {
            String msg = String.format("Thread pool is EXHAUSTED! Pool Size: %d (active: %d, core: %d, max: %d, largest: %d), Task: %d (completed: %d), Executor status:(isShutdown:%s, isTerminated:%s, isTerminating:%s)!",
                    e.getPoolSize(), e.getActiveCount(), e.getCorePoolSize(), e.getMaximumPoolSize(),
                    e.getLargestPoolSize(),
                    e.getTaskCount(), e.getCompletedTaskCount(),
                    e.isShutdown(),
                    e.isTerminated(),
                    e.isTerminating());
            log.warn("{}-{}", taskNamePrefix, msg);
            try {
                e.getQueue().offer(r, QUEUE_TIME_OUT, TimeUnit.SECONDS);
            } catch (InterruptedException e1) {
                throw new RejectedExecutionException("Interrupted waiting for BrokerService.worker", e1);
            }
        });
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(50);
        executor.initialize();
        return executor;
    }
}
