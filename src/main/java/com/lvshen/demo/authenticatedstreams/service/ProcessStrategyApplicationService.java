package com.lvshen.demo.authenticatedstreams.service;

import com.google.common.collect.Maps;
import com.lvshen.demo.authenticatedstreams.praram.ProcessResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-5-9 13:57
 * @since JDK 1.8
 */
@Service
public class ProcessStrategyApplicationService {
    Map<String, ProcessStrategy> map = Maps.newHashMap();

    public ProcessStrategyApplicationService(List<ProcessStrategy> strategyList) {
        strategyList.forEach(strategy -> map.put(strategy.getModuleTypeStr(), strategy));
    }

    public void afterProcessOperation(ProcessResult result) {
        String moduleTypeStr = result.getModuleCode();
        ProcessStrategy processStrategy = map.get(moduleTypeStr);
        if (processStrategy != null) {
            processStrategy.afterProcessOperation(result);
        }
    }

    public void nextProcessOperation(ProcessResult result) {
        String moduleTypeStr = result.getModuleCode();
        ProcessStrategy processStrategy = map.get(moduleTypeStr);
        if (processStrategy != null) {
            processStrategy.nextProcessOperation(result);
        }
    }
}
