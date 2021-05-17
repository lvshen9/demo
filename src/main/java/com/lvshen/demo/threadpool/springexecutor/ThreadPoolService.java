package com.lvshen.demo.threadpool.springexecutor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2021-5-17 11:21
 * @since JDK 1.8
 */
@Service
@Slf4j
public class ThreadPoolService {

    @Async("threadPoolExecutor")
    public void executeAsync() {
        log.info("start executeAsync");

        System.out.println("异步线程开始执行");

        log.info("end executeAsync");
    }
}
