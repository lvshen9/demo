package com.lvshen.demo.distributelock.order;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/3/15 23:36
 * @since JDK 1.8
 */
public class OrderCodedGenerator {

    //自增长序列
    private int i = 0;

    public String getOrderCode() {
        Date now = new Date();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-");
        return simpleDateFormat.format(now) + (++i);
    }

    public static void main(String[] args){
        OrderCodedGenerator generator = new OrderCodedGenerator();
        for (int i = 0; i < 10; i++) {
            System.out.println(generator.getOrderCode());
        }
    }
}
