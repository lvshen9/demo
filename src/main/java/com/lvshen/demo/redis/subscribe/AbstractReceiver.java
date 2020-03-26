package com.lvshen.demo.redis.subscribe;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/3/24 10:18
 * @since JDK 1.8
 */
public abstract class AbstractReceiver {
    public abstract void receiveMessage(Object message);
}
