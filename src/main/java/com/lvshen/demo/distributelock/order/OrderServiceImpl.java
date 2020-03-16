package com.lvshen.demo.distributelock.order;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/3/15 23:44
 * @since JDK 1.8
 */
public class OrderServiceImpl implements OrderService {

    private OrderCodedGenerator ocg = new OrderCodedGenerator();
    @Override
    public void createOrder() {
        //生成订单编号
        String orderCode = ocg.getOrderCode();
        System.out.println(Thread.currentThread().getName() + "=========" + orderCode);
        //其他逻辑

    }
}
