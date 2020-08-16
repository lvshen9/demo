package com.lvshen.demo.design.proxy.dynamic;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/8/8 8:35
 * @since JDK 1.8
 */
public class UserServiceImpl implements UserService{
    @Override
    public void addUser(String name, String password) {
        System.out.println("调用addUser()...");
        System.out.println(String.format("参数为：name[%s],password[%s]",name,password));
    }

    @Override
    public void delUser(String name) {
        System.out.println("调用delUser()");
        System.out.println(String.format("参数为：name[%s]",name));
    }
}
