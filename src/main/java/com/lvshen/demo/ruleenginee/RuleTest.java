package com.lvshen.demo.ruleenginee;

import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2021-4-30 14:28
 * @since JDK 1.8
 */
public class RuleTest {
    public static void main(String[] args) {
        // define facts
        Facts facts = new Facts();
        facts.put("rain", true);

        // define rules
        WeatherRule weatherRule = new WeatherRule();
        Rules rules = new Rules();
        rules.register(weatherRule);

        // fire rules on known facts
        RulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.fire(rules, facts);
    }
}
