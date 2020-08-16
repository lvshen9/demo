package com.lvshen.demo.design.proxy.dynamic;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/8/8 8:48
 * @since JDK 1.8
 */
public class CglibProxy implements MethodInterceptor {
    private Object target;
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("CGLIB动态代理,监听开始...");
        Object invoke = method.invoke(target, objects);
        System.out.println("CGLIB动态代理,监听结束...");
        return invoke;
    }

    public Object getCglibProxy(Object target) {
        this.target = target;
        Enhancer enhancer = new Enhancer();
        //指定父类
        enhancer.setSuperclass(target.getClass());

        enhancer.setCallback(this);
        Object result = enhancer.create();
        return result;
    }
}
