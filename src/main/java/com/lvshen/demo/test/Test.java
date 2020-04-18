package com.lvshen.demo.test;

import java.util.Scanner;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/4/18 14:42
 * @since JDK 1.8
 */
public class Test {


    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();
        int i = fun1(num);
        System.out.println(i);
    }

    public static int fun1(Integer num) throws Exception {
        if (num < 0) {
            throw new Exception("num 不能<0");
        }
        String numStr = num.toString();
        char[] chars = numStr.toCharArray();
        return addSum(chars);

    }

    public static int addSum(char[] chars) {
        int sum = getSum(chars);

        if (sum >= 10) {
            char[] chars1 = Integer.valueOf(sum).toString().toCharArray();
            int sum1 = addSum(chars1);
            if (sum1 < 10) {
                return sum1;
            }
        }
        return sum;
    }

    private static int getSum(char[] chars) {
        int sum = 0;
        for (int i = 0; i < chars.length; i++) {
            String str = String.valueOf(chars[i]);
            sum = sum + Integer.parseInt(str);
        }
        return sum;
    }
}
