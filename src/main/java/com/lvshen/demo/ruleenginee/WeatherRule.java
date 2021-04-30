package com.lvshen.demo.ruleenginee;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2021-4-30 14:09
 * @since JDK 1.8
 */
@Rule(name = "weather rule", description = "if it rains then take an umbrella")
public class WeatherRule {

    @Condition
    public boolean itRains(@Fact("rain") boolean rain) {
        return rain;
    }

    @Action
    public void takeAnUmbrella(Facts facts) {
        System.out.println("It rains, take an umbrella!");
        System.out.println(facts.toString());
    }
}
