package com.lvshen.demo.annotation.sensitive.ruleenginee;

import com.lvshen.demo.annotation.sensitive.SensitiveType;
import com.lvshen.demo.ruleenginee.WeatherRule;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.springframework.stereotype.Component;

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

    public void execute(String str, SensitiveType type) {
        // 封装Facts
        Facts facts = new Facts();
        facts.put("ruleInfo", new RuleEntity(type, str));

        // 注册Rule
        AddressRule addressRule = new AddressRule();
        Rules rules = new Rules();
        rules.register(addressRule);

        // 执行Rule
        RulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.fire(rules, facts);
    }

}
