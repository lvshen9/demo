package com.lvshen.demo.callback;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/9/5 13:30
 * @since JDK 1.8
 */
@Getter
@Setter
@AllArgsConstructor
@Slf4j
public class Buyer implements OrderResult{

    private Store store;//商店
    private String buyerName;//购物者名
    private String goodsName;//所购商品名


    /*调用从商店返回订购物品的信息*/
    public String getOrderInfoSync() {
        String goodsState = store.returnOrderGoodsInfo(this);
        System.out.println(goodsState);
        myFeeling();// 测试同步还是异步, 同步需要等待, 异步无需等待
        return "Function is Over";

    }

    /*调用从商店返回订购物品的信息*/
    public String getOrderInfoAnSync() {
        String goodsState = "--";
        MyRunnable mr = new MyRunnable();
        Thread t = new Thread(mr);
        t.start();
        System.out.println(goodsState);
        goodsState = mr.getResult();// 得到返回值
        myFeeling();// 用来测试异步是不是还是按顺序的执行
        return goodsState;
    }

    public void myFeeling() {
        System.out.println("3.执行完returnOrderGoodsInfo,现在执行myFeeling");
    }

    /*被回调的方法, 我们自己不去调用, 这个方法给出的结果, 是其他接口或者程序给我们的, 我们自己无法产生*/
    @Override
    public String getOrderResult() {
        return String.format("2.回调-调用[getOrderResult]方法，这个是模拟订单返回状态");
    }

    // 开启另一个线程, 但是没有返回值, 怎么回事
    // 调试的时候, 等待一会儿, 还是可以取到值, 但不是立即取到, 在print显示的时候, 却是null, 需要注意?
    @Getter
    @Setter
    private class MyRunnable implements Runnable {
        private String result;

        @Override
        public void run() {
            try {
                Thread.sleep(10000);
                result = store.returnOrderGoodsInfo(Buyer.this);// 匿名函数的时候, 无法return 返回值
            } catch (InterruptedException e) {
                log.error("出大事了, 异步回调有问题了", e);
            }
        }
    }

}
