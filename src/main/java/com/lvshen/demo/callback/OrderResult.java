package com.lvshen.demo.callback;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/9/5 13:28
 * @since JDK 1.8
 */
public interface OrderResult {
    /**
     * 订购货物的状态
     *
     * @param
     * @return
     */
    //参数可以不用, 用不用按照自己的实际需求决定
    String getOrderResult();
}
