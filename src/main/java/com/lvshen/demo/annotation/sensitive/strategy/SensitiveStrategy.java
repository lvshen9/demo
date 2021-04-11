package com.lvshen.demo.annotation.sensitive.strategy;

import com.lvshen.demo.annotation.sensitive.SensitiveType;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2021/4/10 19:04
 * @since JDK 1.8
 */
public interface SensitiveStrategy {

    SensitiveType getSensitiveType();

    String maskingData(String str);
}
