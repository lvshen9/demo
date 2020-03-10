package com.lvshen.demo.arithmetic.snowflake;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/10/30 15:55
 * @since JDK 1.8
 */
public interface IdGenerator {
    /**
     * 获取下一个序列ID
     * @return id
     */
    String nextId();
}
