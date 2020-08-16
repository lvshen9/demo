package com.lvshen.demo.design.proxy.dynamic;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/8/8 8:45
 * @since JDK 1.8
 */
public class Test {

    @org.junit.Test
    public void testJdkProxy() {
        JdkProxy jdkProxy = new JdkProxy();
        UserService userService = (UserService) jdkProxy.getJdkProxy(new UserServiceImpl());

        userService.addUser("lvshen","123456");

    }

    @org.junit.Test
    public void testCglibProxy() {
        CglibProxy cglibProxy = new CglibProxy();
        UserService service = (UserService) cglibProxy.getCglibProxy(new UserServiceImpl());
        service.addUser("zhouzhou","654321");
    }
}
