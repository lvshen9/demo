package com.lvshen.demo.java8.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2024/3/31 19:02
 * @since JDK 1.8
 */
public class BigDecimalUtils {
    /**
     * BigDecimal 做加法，为null时处理为0
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal add(BigDecimal v1, BigDecimal v2) {
        return nullDefaultZero(v1).add(nullDefaultZero(v2));
    }

    public static BigDecimal nullDefaultZero(BigDecimal val) {
        if (null == val) {
            return BigDecimal.ZERO;
        }
        return val;
    }

    /**
     * 返回指定小数位数或整数（如果为整数）
     * @param bigDecimal      目标数
     * @param scale           返回小数时的小数点位数
     * @return
     */
    public static BigDecimal getScaleOrInteger(BigDecimal bigDecimal, int scale) {
        if (bigDecimal == null || BigDecimal.ZERO.compareTo(bigDecimal) == 0) {
            return BigDecimal.ZERO;
        }
        boolean integerNumber = isIntegerNumber(bigDecimal);
        if (integerNumber) {
            return bigDecimal.setScale(0, BigDecimal.ROUND_HALF_UP);
        }
        return bigDecimal.setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    public static boolean isIntegerNumber(BigDecimal number) {
        return number.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0;
    }

    /**
     * 获取 a2除以a1的百分比结果
     * @param a1
     * @param a2
     * @param decimalPlaces 保留小数点位数
     * @return
     */
    public static String percentResult(BigDecimal a1, BigDecimal a2, int decimalPlaces){
        BigDecimal r = a2.divide(a1,decimalPlaces + 2, BigDecimal.ROUND_HALF_UP);
        NumberFormat percent = NumberFormat.getPercentInstance();
        percent.setMaximumFractionDigits(2);
        return percent.format(r.doubleValue());
    }

    /**
     * 小数转百分数
     * @param a1                小数
     * @param decimalPlaces     保留的小数位数
     * @return                  百分数
     */
    public static String decimal2Percent(BigDecimal a1,int decimalPlaces) {
        BigDecimal r = a1.setScale(decimalPlaces, BigDecimal.ROUND_HALF_UP);
        NumberFormat percent = NumberFormat.getPercentInstance();
        percent.setMaximumFractionDigits(2);
        return percent.format(r.doubleValue());
    }

    /**
     * 默认保留2位小数
     * @param a1
     * @return
     */
    public static String decimal2Percent(BigDecimal a1) {
        return decimal2Percent(a1, 2);
    }
}
