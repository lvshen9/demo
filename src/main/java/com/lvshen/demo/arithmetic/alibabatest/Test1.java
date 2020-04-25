package com.lvshen.demo.arithmetic.alibabatest;

/**
 * Description:面试题002 已知sqrt (2)约等于1.414，要求不用数学库，求sqrt (2)精确到小数点后10位。
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/4/24 17:40
 * @since JDK 1.8
 */
public class Test1 {
    private static final double EPSINON = 0.0000000001;

    public static double sqrt2() {
        double low = 1.4;
        double high = 1.5;

        double mid = (low + high) / 2;

        while (high - low > EPSINON) {
            if ((mid * mid > 2)) {
                high = mid;
            } else {
                low = mid;
            }
            mid = (high + low) / 2;
        }
        return mid;
    }

    public static void main(String[] args){
        System.out.println(sqrt2());
    }
}
