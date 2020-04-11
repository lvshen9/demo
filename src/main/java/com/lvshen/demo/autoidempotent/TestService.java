package com.lvshen.demo.autoidempotent;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/4/11 10:25
 * @since JDK 1.8
 */
public interface TestService {
    ServerResponse testIdempotence();
}
