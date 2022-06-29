package com.lvshen.demo.easyexcel;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-6-29 14:00
 * @since JDK 1.8
 */
@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean
    public Executor taskAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程池数量，方法: 返回可用处理器的Java虚拟机的数量。
        executor.setCorePoolSize(5);
        //最大线程数量
        executor.setMaxPoolSize(10);
        //线程池的队列容量
        executor.setQueueCapacity(20);
        // 设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(60);
        //线程名称的前缀
        executor.setThreadNamePrefix("thread-");
        // setRejectedExecutionHandler：当pool已经达到max size的时候，如何处理新任务
        // CallerRunsPolicy：不在新线程中执行任务，而是由调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }

    @Bean("excelThreadPool")
    public ExecutorService buildExcelThreadPool() {
        int cpuNum = Runtime.getRuntime().availableProcessors();
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(1000);
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("excel-pool-%d").build();
        return new ThreadPoolExecutor(10 * cpuNum, 30 * cpuNum,
                40, TimeUnit.MINUTES, workQueue, threadFactory);
    }
}
