package com.lvshen.demo.securerandom;

import org.junit.Test;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/7/3 16:42
 * @since JDK 1.8
 */

public class SecureRandomTest {
	@Test
	public void test1() {
		int checkBitLen = 2;
		int codeLen = 3;
		int flagBitLen = 4;

		Long randomTest = randomTest(checkBitLen, codeLen, flagBitLen);
		Long secureRandomTest = secureRandomTest(checkBitLen, codeLen, flagBitLen);

		System.out.println("randomTest: " + randomTest);
		System.out.println("secureRandomTest: " + secureRandomTest);

	}

	@Test
	public void test2() {
        byte[] bytes = new byte[128];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.setSeed(System.currentTimeMillis());
        secureRandom.nextBytes(bytes);
    }

	private Long randomTest(int checkBitLen, int codeLen, int flagBitLen) {
		Random random = new Random();
		int totalBitLen = codeLen * 5;
		int dataBitLen = totalBitLen - checkBitLen - flagBitLen;
		long randData = (long) (1 + (1L << dataBitLen - 1) * random.nextDouble());
		return randData;
	}

	private Long secureRandomTest(int checkBitLen, int codeLen, int flagBitLen) {
		SecureRandom secureRandom = new SecureRandom();
		int totalBitLen = codeLen * 5;
		int dataBitLen = totalBitLen - checkBitLen - flagBitLen;
		long randData = (long) (1 + (1L << dataBitLen - 1) * secureRandom.nextDouble());
		return randData;
	}

}
