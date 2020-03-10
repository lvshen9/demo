package com.lvshen.demo.design.abstractfactory;


import com.lvshen.demo.design.builder.Product;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/12/30 17:58
 * @since JDK 1.8
 */
public interface AbstractFactory {
    public Product product();
}
