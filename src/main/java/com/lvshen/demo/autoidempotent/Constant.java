package com.lvshen.demo.autoidempotent;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/4/11 11:17
 * @since JDK 1.8
 */
public class Constant {

    public interface Redis {
        String OK = "OK";
        Integer EXPIRE_TIME_MINUTE = 60;// 过期时间, 60s, 一分钟
        Integer EXPIRE_TIME_HOUR = 60 * 60;// 过期时间, 一小时
        Integer EXPIRE_TIME_DAY = 60 * 60 * 24;// 过期时间, 一天
        String TOKEN_PREFIX = "token:";
        String MSG_CONSUMER_PREFIX = "consumer:";
        String ACCESS_LIMIT_PREFIX = "accessLimit:";
    }
}
