package com.lvshen.demo.annotation.sensitive.ruleenginee;

import com.lvshen.demo.annotation.sensitive.SensitiveInfoUtils;
import com.lvshen.demo.annotation.sensitive.SensitiveType;
import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;
import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2021/4/10 19:04
 * @since JDK 1.8
 */
@Component
@Rule(name = "ChineseNameRule", description = "中文过滤规则")
public class ChineseNameRule implements SensitiveRule {

    @Override
    @Condition
    public boolean isCurrentType(@Fact("ruleInfo") RuleEntity ruleInfo) {
        return SensitiveType.CHINESE_NAME == ruleInfo.getType();
    }

    @Override
    @Action
    public String maskingData(Facts facts) {
        RuleEntity ruleInfo = facts.get("ruleInfo");
        String infoStr = ruleInfo.getInfoStr();
        SensitiveInfoUtils.chineseName(infoStr);
        return ruleInfo.toString();
    }
}
