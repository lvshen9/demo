package com.lvshen.demo.genetic;

import com.lvshen.demo.treenode.Student;

import java.lang.reflect.ParameterizedType;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-8-31 10:12
 * @since JDK 1.8
 */
public class Foo<T> {
    public Class<T> getTClass() {
        Class<T> tClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return tClass;
    }

    public static void main(String[] args){
        Foo<String> studentFoo = new Foo<String>();
        Class<String> tClass = studentFoo.getTClass();
        System.out.println(tClass);

    }
}
