package com.lvshen.demo.spring;

import com.lvshen.demo.spring.annotation.MyService;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/3/26 19:37
 * @since JDK 1.8
 */
@MyService
public class DemoService implements IDemoService {
    @Override
    public String get(String name) {
        return "My name is " + name + ", from service";
    }
}
