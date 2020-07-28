package com.lvshen.demo.threadlocal;

import io.netty.util.internal.ConcurrentSet;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/10/21 10:44
 * @since JDK 1.8
 */
public class ThreadTest {
	/**
	 * threadLocal Test
	 */
	public static ThreadLocal<String> threadLocal = new ThreadLocal<String>();

	public static String baseString = "";

	private volatile long sequenceBits = 7L;

	private volatile long workerIdBits = 8L;

	public static ExecutorService executorService
			= new ThreadPoolExecutor(4, 4, 0L, TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<>());

	private static void setTLocal(String value) {
		threadLocal.set(value);
	}

	private static String getTLocal() {
		return "ThreadLocal保存:" + Thread.currentThread().getName() + " : " + threadLocal.get();
	}

	private static void setBLocal(String value) {
		baseString = value;
	}

	private static String getBLocal() {
		return "Baseocal保存:   " + Thread.currentThread().getName() + " : " + baseString;
	}

	public static void main(String[] args) throws InterruptedException {

		Thread thread = new Thread(() -> {
			setTLocal("子线程一保存变量");
			try {
				// 睡眠一秒，模拟在处理某些程序
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(getTLocal());
		});

		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				setTLocal("子线程二保存变量");
				System.out.println(getTLocal());
			}
		});

		executorService.execute(thread);
		executorService.execute(thread2);

		setTLocal("主线程保存变量");
		System.out.println(getTLocal());

		Thread.sleep(1000);

		Thread thread3 = new Thread(() -> {
			setBLocal("子线程一保存变量");
			try {
				// 睡眠一秒，模拟在处理某些程序
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.err.println(getBLocal());
		});

		Thread thread4 = new Thread(() -> {
			setBLocal("子线程二保存变量");
			System.err.println(getBLocal());
		});

		executorService.execute(thread3);
		executorService.execute(thread4);

		setBLocal("主线程保存变量");
		System.err.println(getBLocal());
	}

	@Test
	public void testVolatile() {
		Thread thread = new Thread(() -> {
			for (int i = 0; i < 100; i++) {
				sequenceBits ++;
				System.out.println(Thread.currentThread().getName() + ":" + sequenceBits);
			}
		});

		thread.start();

		Thread thread2 = new Thread(() -> {
			for (int i = 0; i < 100; i++) {
				sequenceBits ++;
				System.out.println(Thread.currentThread().getName() + ":" + sequenceBits);
			}
		});

		thread2.start();
	}

	@Test
	public void testSet() {
		ConcurrentSet<String> concurrentSet = new ConcurrentSet<>();
		concurrentSet.add("xiaoming");
		System.out.println(concurrentSet);
	}

}
