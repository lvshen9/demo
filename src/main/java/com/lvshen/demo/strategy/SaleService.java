package com.lvshen.demo.strategy;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/10/10 21:22
 * @since JDK 1.8
 */

@Service
public class SaleService {

    /*@Autowired
    private VipStrategy vipStrategy;

    @Autowired
    private NormalStrategy normalStrategy;*/

    Map<String, CalculateStrategy> map = new HashMap<>();

    public SaleService(List<CalculateStrategy> calculateStrategyList) {
        calculateStrategyList.forEach(calculateStrategy -> map.put(calculateStrategy.userLevel(), calculateStrategy));
    }

    /**
     * 获取真实的价格
     *
     * @param level
     * @param originalPrice
     * @return
     */
    public BigDecimal getActualPrice(String level, BigDecimal originalPrice) {
        if ("normal".equals(level)) {
            return originalPrice.multiply(new BigDecimal("1.0"));
        } else if ("vip".equals(level)) {
            return originalPrice.multiply(new BigDecimal("0.9"));
        }
        return originalPrice;
    }

    public BigDecimal getActualPriceWithStrategy(String level, BigDecimal originalPrice) {
        CalculateStrategy calculateStrategy = map.get(level);
        return calculateStrategy == null ? originalPrice : calculateStrategy.discount(originalPrice);
    }
}
