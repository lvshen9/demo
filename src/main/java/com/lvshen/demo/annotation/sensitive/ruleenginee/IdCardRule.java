package com.lvshen.demo.annotation.sensitive.ruleenginee;

import com.lvshen.demo.annotation.sensitive.SensitiveInfoUtils;
import com.lvshen.demo.annotation.sensitive.SensitiveType;
import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2021/4/10 19:04
 * @since JDK 1.8
 */
@Component
@Rule(name = "IdCardRule", description = "身份证号码过滤规则")
public class IdCardRule implements SensitiveRule {

    @Override
    public SensitiveType getCurrentSensitiveType() {
        return SensitiveType.ID_CARD;
    }

    @Override
    @Condition
    public boolean isCurrentType(@Fact("ruleInfo") RuleEntity ruleInfo) {
        return getCurrentSensitiveType() == ruleInfo.getType();
    }

    @Override
    @Action
    public void maskingData(Facts facts) {
        RuleEntity ruleInfo = facts.get("ruleInfo");
        String infoStr = ruleInfo.getInfoStr();
        String idCardNum = SensitiveInfoUtils.idCardNum(infoStr);
        Map<String, String> map = RuleMapService.getMap();
        map.put("maskingData", idCardNum);
    }
}
