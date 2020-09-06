package com.lvshen.demo.callback;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/9/5 13:32
 * @since JDK 1.8
 */
public class Test {

    /*
    *  Buyer#getOrderInfoSync
    *         -> Store#returnOrderGoodsInfo
    *              ->order#getOrderResult[Buyer#getOrderResult]
    * */

    @org.junit.Test
    public void test2Callback() {
        Store wallMart = new Store("Lvshen的技术小屋");
        Buyer buyer = new Buyer(wallMart, "Lvshen", "Java资料");
        System.out.println(buyer.getOrderInfoSync());
    }

    @org.junit.Test
    public void test2Callback2() {
        Store wallMart = new Store("Lvshen的技术小屋");
        Buyer buyer = new Buyer(wallMart, "Lvshen", "Java资料");
        System.out.println(buyer.getOrderInfoAnSync());


        System.out.println("\n");
        Store lawson = new Store("Lvshen_9");
        Buyer noBuyer = new Buyer(lawson, "Lvshen", "算法资料");
        System.out.println(noBuyer.getOrderInfoAnSync());
    }

}
