package com.lvshen.demo.logexception.apilog;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-9-5 15:27
 * @since JDK 1.8
 */
@Component
@Slf4j
public class RetryResultApplicationService {
    Map<String, RetryResultHandler> map = Maps.newConcurrentMap();

    public RetryResultApplicationService(List<RetryResultHandler> retryResultHandlers) {
        retryResultHandlers.forEach(handler -> {
            String invokeMethodStr = handler.invokeMethodStr();
            String methodName = handler.methodName();
            String key = invokeMethodStr.concat("#").concat(methodName);
            map.put(key, handler);
        });
    }

    public void resultHandler(String executeClassStr, Object o) {
        if (StringUtils.isBlank(executeClassStr)) {
            log.info("目标方法的父层路径【{}】为空", executeClassStr);
            return;
        }
        RetryResultHandler handler = map.get(executeClassStr);
        if (handler != null) {
            handler.resultHandler(o);
        }
    }
}
