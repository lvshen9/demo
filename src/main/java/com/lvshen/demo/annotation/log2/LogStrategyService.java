package com.lvshen.demo.annotation.log2;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2024/3/31 19:13
 * @since JDK 1.8
 */
@Component
public class LogStrategyService {
    Map<String, LogStrategy> map = new ConcurrentHashMap<>();

    public LogStrategyService(List<LogStrategy> strategyList) {
        strategyList.forEach(strategy -> map.put(strategy.getModuleTypeStr(), strategy));
    }

    public void afterHandler(LogResult result) {
        String moduleTypeStr = result.getModuleType();
        LogStrategy processStrategy = map.get(moduleTypeStr);
        if (processStrategy != null) {
            processStrategy.afterHandler(result);
        }
    }

}
