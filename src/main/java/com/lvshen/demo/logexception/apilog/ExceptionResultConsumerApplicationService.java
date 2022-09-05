package com.lvshen.demo.logexception.apilog;

import com.lvshen.demo.redis.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-9-5 15:24
 * @since JDK 1.8
 */
@Component
@Slf4j
public class ExceptionResultConsumerApplicationService implements ApplicationRunner {

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private LogExecutor logExecutor;
    @Autowired
    private AutoRetryFlagApplicationService autoRetryFlagApplicationService;

    private static final String EXCEPTION_QUEUE_KEY = "lvshen:exception:method:retry";

    private void loop() {
        String name = Thread.currentThread().getName();
        log.info("异常方法重试线程启动，当前线程：【{}】", name);
        while (!Thread.interrupted()) {
            String apiLogId = (String) redisUtils.blockRightPop(EXCEPTION_QUEUE_KEY);
            LogExecutor.LogMethodParam param = new LogExecutor.LogMethodParam();
            param.setLogId(apiLogId);
            logExecutor.executorMethod(param);

            //每隔5s循环一次。减少资源消耗
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //暂时先不启用
        if (autoRetryFlagApplicationService.getNeedAuto()) {
            loop ();
        }
    }
}
