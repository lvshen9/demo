package com.lvshen.demo.annotation.easyexport;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-12-20 8:37
 * @since JDK 1.8
 */
@Configuration
@ConditionalOnProperty(name = "basic-service.excel.pool.provider", havingValue = "default")
public class ExcelPoolTask {
    @Bean("excelThreadPool")
    public ExecutorService buildExcelThreadPool() {
        int cpuNum = Runtime.getRuntime().availableProcessors();
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(1000);
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("lvshen-async-excel-pool-%d").build();
        return new ThreadPoolExecutor(10 * cpuNum, 30 * cpuNum,
                40, TimeUnit.MINUTES, workQueue, threadFactory);
    }
}
