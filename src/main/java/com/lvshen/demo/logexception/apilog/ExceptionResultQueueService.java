package com.lvshen.demo.logexception.apilog;

import com.lvshen.demo.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-9-5 15:16
 * @since JDK 1.8
 */
@Component
public class ExceptionResultQueueService {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private AutoRetryFlagApplicationService autoRetryFlagApplicationService;

    private static final String EXCEPTION_QUEUE_KEY = "lvshen:exception:method:retry";

    public void pushExceptionQueue(String apiLogId) {
        if (autoRetryFlagApplicationService.getNeedAuto()) {
            redisUtils.leftPush(EXCEPTION_QUEUE_KEY, apiLogId);
        }
    }
}
