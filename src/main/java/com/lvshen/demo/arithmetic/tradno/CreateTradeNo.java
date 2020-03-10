package com.lvshen.demo.arithmetic.tradno;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/7/23 9:41
 * @since JDK 1.8
 */
public class CreateTradeNo {
	public static void main(String[] args) throws UnknownHostException {
		// 1.两位随机数+13位时间戳
		int r1 = (int) (Math.random() * (10));// 产生2个0-9的随机数
		int r2 = (int) (Math.random() * (10));
		long now = System.currentTimeMillis();// 一个13位的时间戳
		String paymentID = String.valueOf(r1) + String.valueOf(r2) + String.valueOf(now);// 订单ID
		System.out.println(paymentID);

		// 2.年月日+用户主键.hashCode()+ip
		InetAddress addrs = InetAddress.getLocalHost();
		System.out.println("Local HostAddress: " + addrs.getHostAddress());
		String hostName = addrs.getHostName();
		System.out.println("Local host name: " + hostName);
		String userId = "admin";

		// 当前时间
		Date date = new Date();
		// 转换格式
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		// 格式化
		String time = simpleDateFormat.format(date);
		// ip地址
		String ip = addrs.getHostAddress();
		ip = ip.replace(".", "");

		System.currentTimeMillis(); // 获取毫秒

		String id = time + ip + userId.hashCode() + System.currentTimeMillis();
		System.out.println("生成唯一订单号" + id);
		System.out.println("订单号" + ip + System.currentTimeMillis());
	}
}
