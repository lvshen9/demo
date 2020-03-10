package com.lvshen.demo;

import com.google.common.base.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/4/28 20:45
 * @since JDK 1.8
 */
@Slf4j
public class GuavaTest {

	@Test
	public void test() {
		Integer s = null;
		Optional<Integer> of = Optional.of(s);
		System.out.println(of.isPresent());

		System.out.println(of.get());

	}

}
