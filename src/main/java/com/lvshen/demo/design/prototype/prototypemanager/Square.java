package com.lvshen.demo.design.prototype.prototypemanager;

import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/12/30 14:20
 * @since JDK 1.8
 */
@Slf4j
public class Square implements Shape {
	@Override
	public Object clone() {
		Square b = null;
		try {
			b = (Square) super.clone();
		} catch (CloneNotSupportedException e) {
			log.info("拷贝正方形失败!");

		}
		return b;
	}

	@Override
	public void countArea() {
		int a = 0;
		System.out.print("这是一个正方形，请输入它的边长：");
		Scanner input = new Scanner(System.in);
		a = input.nextInt();
		System.out.println("该正方形的面积=" + a * a + "\n");
	}
}
