package com.lvshen.demo.design.proxy.dynamic;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/8/8 8:33
 * @since JDK 1.8
 */
public interface UserService {
    void addUser(String name, String password);

    void delUser(String name);
}
