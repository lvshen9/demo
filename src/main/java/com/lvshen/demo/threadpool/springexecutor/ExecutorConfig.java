package com.lvshen.demo.threadpool.springexecutor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2021-5-17 11:09
 * @since JDK 1.8
 */
@Configuration
@EnableAsync
@Slf4j
public class ExecutorConfig {

    @Value("${thread.pool.core_pool_size}")
    private int corePoolSize;
    @Value("${thread.pool.max_pool_size}")
    private int maxPoolSize;
    @Value("${thread.pool.queue_capacity}")
    private int queueCapacity;
    @Value("${thread.pool.name.prefix}")
    private String namePrefix;

    @Bean(name = "threadPoolExecutor")
    public Executor threadPoolExecutor() {
        log.info("start threadPoolExecutor");
        ThreadPoolTaskExecutor executor = new ThreadPoolMonitor();
        //配置核心线程数
        executor.setCorePoolSize(corePoolSize);
        //配置最大线程数
        executor.setMaxPoolSize(maxPoolSize);
        //配置队列大小
        executor.setQueueCapacity(queueCapacity);
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix(namePrefix);

        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        executor.initialize();
        return executor;
    }
}
