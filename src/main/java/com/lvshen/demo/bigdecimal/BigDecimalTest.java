package com.lvshen.demo.bigdecimal;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/7/1 9:49
 * @since JDK 1.8
 */
public class BigDecimalTest {
    @Test
    public void test(){
        BigDecimal bigDecimal = new BigDecimal(0.1);
        System.out.println(bigDecimal);

        BigDecimal bigDecimal1 = new BigDecimal(10);
        System.out.println(bigDecimal1);

        BigDecimal bigDecimal2 = new BigDecimal("0.1");
        System.out.println(bigDecimal2);

        BigDecimal bigDecimal3 = BigDecimal.valueOf(0.1);
        System.out.println(bigDecimal3);
    }

    @Test
    public void test2() {
        Long usablePoint = 0L;
        System.out.println(usablePoint.intValue());
    }

}
