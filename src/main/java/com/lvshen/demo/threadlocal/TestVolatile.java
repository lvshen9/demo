package com.lvshen.demo.threadlocal;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/10/31 11:26
 * @since JDK 1.8
 */
public class TestVolatile {
	public volatile int inc = 0;

	public void increase() {
		inc++;
	}

	public static void main(String[] args) {
		final TestVolatile test = new TestVolatile();
		for (int i = 0; i < 10; i++) {
			new Thread() {
				@Override
                public void run() {
					for (int j = 0; j < 1000; j++) {
                        test.increase();
                    }
				};
			}.start();
		}

		while (Thread.activeCount() > 1) // 保证前面的线程都执行完
        {
            Thread.yield();
        }
		System.out.println(test.inc);
	}
}
