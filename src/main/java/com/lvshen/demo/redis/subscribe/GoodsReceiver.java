package com.lvshen.demo.redis.subscribe;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/3/24 10:18
 * @since JDK 1.8
 */
@Slf4j
public class GoodsReceiver extends AbstractReceiver {
    @Override
    public void receiveMessage(Object message) {
        log.info("接收到商品消息：{}", JSON.toJSONString(message));
    }
}
