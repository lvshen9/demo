package com.lvshen.demo.strategy;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/10/10 21:40
 * @since JDK 1.8
 */
@Component
public class VipStrategy implements CalculateStrategy {
    @Override
    public String userLevel() {
        return "vip";
    }

    @Override
    public BigDecimal discount(BigDecimal originalPrice) {
        return originalPrice.multiply(new BigDecimal("0.9"));
    }
}
