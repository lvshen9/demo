package com.lvshen.demo.design.prototype.prototypemanager;

import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/12/30 14:17
 * @since JDK 1.8
 */
@Slf4j
public class Circle implements Shape {
	@Override
	public Object clone() {
		Circle w = null;
		try {
			w = (Circle) super.clone();
		} catch (CloneNotSupportedException e) {
			log.info("拷贝圆失败!");
		}
		return w;
	}

	@Override
	public void countArea() {
		int r = 0;
		System.out.print("这是一个圆，请输入圆的半径：");
		Scanner input = new Scanner(System.in);
		r = input.nextInt();
        System.out.println("该圆的面积=" + 3.1415 * r * r + "\n");
	}
}
