package com.lvshen.demo.annotation.sensitive.ruleenginee;

import com.lvshen.demo.annotation.sensitive.SensitiveType;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.api.Facts;
import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2021-4-30 15:34
 * @since JDK 1.8
 */
@Component
public interface SensitiveRule {

    SensitiveType getCurrentSensitiveType();
    boolean isCurrentType(@Fact("ruleInfo") RuleEntity ruleInfo);
    void maskingData(Facts facts);
}
