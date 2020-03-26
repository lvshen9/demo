package com.lvshen.demo.redis.subscribe;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/3/24 10:19
 * @since JDK 1.8
 */
@Slf4j
public class UserReceiver extends AbstractReceiver {
    @Override
    public void receiveMessage(Object message) {
        log.info("接收到用户消息：{}", JSON.toJSONString(message));
    }
}
