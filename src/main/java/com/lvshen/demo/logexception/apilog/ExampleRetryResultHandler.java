package com.lvshen.demo.logexception.apilog;

import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-9-5 15:23
 * @since JDK 1.8
 */
@Component
public class ExampleRetryResultHandler implements RetryResultHandler{
    @Override
    public String resultHandler(Object obj) {
        //重试方法执行后业务处理
        return null;
    }

    @Override
    public String invokeMethodStr() {
        return "example";
    }

    @Override
    public String methodName() {
        return "sendTaskMessage";
    }
}
