package com.lvshen.demo.design.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/8/8 8:39
 * @since JDK 1.8
 */
public class JdkProxy  implements InvocationHandler {
    //需要代理的目标对象
    private Object target;
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("JDK动态代理,监听开始...");
        Object invoke = method.invoke(target, args);
        System.out.println("JDK动态代理,监听结束...");
        return invoke;
    }

    public Object getJdkProxy(Object targetObject) {
        this.target = targetObject;
        Object o = Proxy.newProxyInstance(targetObject.getClass().getClassLoader(), targetObject.getClass().getInterfaces(), this);
        //实例化
        return o;
    }
}
