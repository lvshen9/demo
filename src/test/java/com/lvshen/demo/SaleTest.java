package com.lvshen.demo;

import cn.hutool.core.lang.Console;
import com.lvshen.demo.strategy.SaleService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/12/26 14:52
 * @since JDK 1.8
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class SaleTest {

    @Autowired
    private SaleService saleService;

    @Test
    public void test1() {
        BigDecimal originalPrice = new BigDecimal("100");
        String level = "vip";
        BigDecimal actualPrice = saleService.getActualPrice(level, originalPrice);
        Console.log("getActualPrice()测试的真实价格为：{}",actualPrice);
    }

    @Test
    public void test2() {
        BigDecimal originalPrice = new BigDecimal("100");
        String level = "vip";
        BigDecimal actualPriceWithStrategy = saleService.getActualPriceWithStrategy(level, originalPrice);
        Console.log("getActualPriceWithStrategy()测试的真实价格为：{}",actualPriceWithStrategy);
    }
}
