package com.lvshen.demo.strategy;

import java.math.BigDecimal;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/10/10 21:38
 * @since JDK 1.8
 */
public interface CalculateStrategy {
    String userLevel();
    BigDecimal discount(BigDecimal originalPrice);
}
