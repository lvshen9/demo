package com.lvshen.demo.callback;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/9/5 13:29
 * @since JDK 1.8
 */
@Getter
@Setter
@AllArgsConstructor
public class Store {

    private String name;

    /*回调函数, 将结构传给那个我们不能直接调用的方法, 然后获取结果*/
    public String returnOrderGoodsInfo(OrderResult order) {
        System.out.println("1.调用returnOrderGoodsInfo,获取订单结果[接下来会执行getOrderResult]");
        return order.getOrderResult();
    }
}
