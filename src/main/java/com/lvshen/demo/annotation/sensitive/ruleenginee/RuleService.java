package com.lvshen.demo.annotation.sensitive.ruleenginee;

import com.google.common.collect.Maps;
import com.lvshen.demo.annotation.sensitive.SensitiveType;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2021-4-30 16:29
 * @since JDK 1.8
 */
@Component
public class RuleService {

    Map<SensitiveType, SensitiveRule> map = Maps.newHashMap();

    public RuleService(List<SensitiveRule> sensitiveStrategyList) {
        sensitiveStrategyList.forEach(sensitiveRule -> map.put(sensitiveRule.getCurrentSensitiveType(), sensitiveRule));
    }

    public String execute(SensitiveType type, String str) {
        // 封装Facts
        Facts facts = new Facts();
        facts.put("ruleInfo", new RuleEntity(type, str));

        // 注册Rule
        Rules rules = new Rules();
        map.forEach((k, v) -> rules.register(v));

        // 执行Rule
        RulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.fire(rules, facts);
        Map<String, String> map = RuleMapService.getMap();
        return map.get("maskingData");
    }
}
