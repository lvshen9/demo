package com.lvshen.demo.arithmetic.maxCommon;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/11/8 17:06
 * @since JDK 1.8
 */
@Slf4j
public class MaxCommonTest {

	@Test
	public void test1() {
		int n = 5;
		int m = 20;

		int maxCommon = getMaxCommon(m, n);
		System.out.println("最大公约数：" + maxCommon);
		System.out.println("最小公倍数：" + n * m / maxCommon);
	}

    @Test
    public void test2() {
        int n = 6;
        int m = 20;

        int maxCommon = getMaxCommonFromEuclid(n, m);
        System.out.println("最大公约数：" + maxCommon);
        System.out.println("最小公倍数：" + n * m / maxCommon);
    }
	/**
	 * 普通算法
	 * 
	 * @param n
	 * @param m
	 * @return
	 */
	private int getMaxCommon(int n, int m) {
		if (n == 0 || m == 0) {
			return 0;
		}
		int smaller;
		int larger;
		int value = 1;

		if (n < m) {
			smaller = n;
			larger = m;
		} else {
			smaller = m;
			larger = n;
		}

		for (int index = 2; index <= smaller; index++) {
			if (0 == (smaller % index) && 0 == (larger % index)) {
				value = index;
			}
		}
		return value;
	}

    /**
     * 欧几里得算法
     * 两个非零整数的最大公约数等于其中较小的那个数和两个数相除余数的最大公约数
     * @param n
     * @param m
     * @return
     */
	private int getMaxCommonFromEuclid(int m, int n) {
	    log.info("testStart....");
        // m和n的最大公约数 = n 和 m%n的最大公约数
		if (n == 0) {
			return m;
		} else {
			return getMaxCommonFromEuclid(n, m % n);
		}
	}
}
