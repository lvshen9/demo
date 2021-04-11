package com.lvshen.demo.annotation.sensitive.strategy;

import com.lvshen.demo.annotation.sensitive.SensitiveInfoUtils;
import com.lvshen.demo.annotation.sensitive.SensitiveType;
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
public class EmailStrategy implements SensitiveStrategy {

    @Override
    public SensitiveType getSensitiveType() {
        return SensitiveType.EMAIL;
    }

    @Override
    public String maskingData(String str) {
        return SensitiveInfoUtils.email(str);
    }
}
