package com.lvshen.demo.annotation.sensitive.ruleenginee;

import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.api.Facts;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2021-4-30 15:34
 * @since JDK 1.8
 */
public interface SensitiveRule {
    boolean isCurrentType(@Fact("ruleInfo") RuleEntity ruleInfo);
    String maskingData(Facts facts);
}
