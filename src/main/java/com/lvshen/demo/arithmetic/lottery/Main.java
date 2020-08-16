package com.lvshen.demo.arithmetic.lottery;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.lvshen.demo.arithmetic.lottery.DrawLotteryService.drawGift;


/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/7/16 9:56
 * @since JDK 1.8
 */
public class Main {

	public static void main(String[] args) {
		Gift iphone = new Gift();
		iphone.setId(101);
		iphone.setName("华为手机");
		iphone.setProb(0.1D);

		Gift thanks = new Gift();
		thanks.setId(102);
		thanks.setName("谢谢参与");
		thanks.setProb(0.8D);

		Gift vip = new Gift();
		vip.setId(103);
		vip.setName("腾讯会员");
		vip.setProb(0.1D);

		List<Gift> list = new ArrayList<Gift>();
		list.add(vip);
		list.add(thanks);
		list.add(iphone);

		int index = drawGift(list);
		System.out.println(list.get(index));

       /* ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        double v = threadLocalRandom.nextDouble(0, 1);
        System.out.println(v);
        System.out.println(new BigDecimal(v).setScale(4, BigDecimal.ROUND_HALF_UP));
		System.out.println("=============================================");
		BigDecimal redpacketMinAmount = new BigDecimal("1.0");
		BigDecimal redpacketMaxAmount = new BigDecimal("5.0");
		double nextDouble = threadLocalRandom.nextDouble(redpacketMinAmount.doubleValue(), redpacketMaxAmount.doubleValue());
		BigDecimal radom = new BigDecimal(nextDouble).setScale(2, BigDecimal.ROUND_HALF_UP);
		System.out.println("radom: " + radom);
		System.out.println("=============================================");
		Long a = 4L;
		Long b = 5L;
		Long l = a / b;
		System.out.println(l.intValue());*/
    }
}
