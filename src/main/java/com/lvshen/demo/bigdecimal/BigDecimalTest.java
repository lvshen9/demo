package com.lvshen.demo.bigdecimal;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * Description: bigdecimal数据精度问题
 *
 * @author lvshen
 * @version 1.0
 * @date: 2019/7/1 9:49
 * @since JDK 1.8
 */
public class BigDecimalTest {
    @Test
    public void test() {
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

    @Test
    public void test3() {
        //BigDecimal param1 = new BigDecimal(0.1);
        //BigDecimal param2 = new BigDecimal(0.2);
        BigDecimal param1 = new BigDecimal("0.1");
        BigDecimal param2 = new BigDecimal("0.2");
        //加法
        BigDecimal add = param1.add(param2);
        System.out.println("加法：" + add);

        //减法
        BigDecimal subtract = param2.subtract(param1);
        System.out.println("减法：" + subtract);

        //乘法
        BigDecimal multiply = param1.multiply(param2);
        System.out.println("乘法：" + multiply);

        //除法
        BigDecimal divide = param2.divide(param1);
        System.out.println("除法：" + divide);
    }

    @Test
    public void test4() {
        Double dParam = 100d;
        BigDecimal bParam = new BigDecimal("0.02");
        BigDecimal toBigDecimal = new BigDecimal(dParam.toString());
        BigDecimal multiply = bParam.multiply(toBigDecimal);
        System.out.println(multiply.setScale(2, BigDecimal.ROUND_HALF_EVEN));
    }

    @Test
    public void test5() {
        BigDecimal bParam = new BigDecimal("1");
        BigDecimal bParam2 = new BigDecimal("3");
        BigDecimal divide = bParam.divide(bParam2, 3, BigDecimal.ROUND_HALF_EVEN);
        System.out.println(divide);
    }

    @Test
    public void test6() {
        System.out.println(new BigDecimal("1.0").compareTo(new BigDecimal("1")));
    }

}
