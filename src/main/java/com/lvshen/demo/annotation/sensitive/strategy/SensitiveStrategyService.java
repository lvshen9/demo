package com.lvshen.demo.annotation.sensitive.strategy;

import com.google.common.collect.Maps;
import com.lvshen.demo.annotation.sensitive.SensitiveType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2021/4/10 19:41
 * @since JDK 1.8
 */
@Service
public class SensitiveStrategyService {
    Map<SensitiveType, SensitiveStrategy> map = Maps.newHashMap();

    public SensitiveStrategyService(List<SensitiveStrategy> sensitiveStrategyList) {
        sensitiveStrategyList.forEach(sensitiveStrategy -> map.put(sensitiveStrategy.getSensitiveType(), sensitiveStrategy));
    }

    public String generatorSensitive(SensitiveType typeEnum, String str) {
        SensitiveStrategy sensitiveStrategy = map.get(typeEnum);
        if (sensitiveStrategy != null) {
            return sensitiveStrategy.maskingData(str);
        }
        return StringUtils.EMPTY;
    }

}
