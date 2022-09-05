package com.lvshen.demo.logexception.apilog;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-9-5 15:17
 * @since JDK 1.8
 */
@Component
public class AutoRetryFlagApplicationService {
    @Value("${lvshen.auto.retry.exception.method}")
    private Boolean needAuto;

    public boolean getNeedAuto() {
        return needAuto;
    }
}
