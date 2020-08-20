package com.lvshen.demo.enumtest;

import java.lang.reflect.Constructor;
import java.util.EnumMap;
import java.util.EnumSet;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-8-20 8:03
 * @since JDK 1.8
 */
public class Test {

    @org.junit.Test
    public void test1(){
        String type = "";

        if ("PANDA".equals(type)) {
            System.out.println("吃竹子");
        } else if("CAT".equals(type)) {
            System.out.println("吃鱼");
        } else if("MONKEY".equals(type)) {
            System.out.println("吃香蕉");
        }
    }

    @org.junit.Test
    public void test2() {
        String type = "CAT";
        String eat = AnimalEnum.valueOf(type).eat();
        System.out.println(eat);

    }

    @org.junit.Test
    public void test3() {
        EnumMap<TestEnum, String> enumMap = new EnumMap<>(TestEnum.class);
        enumMap.put(TestEnum.TEACHER,"教书");
        enumMap.put(TestEnum.STUDENT,"学习");
        enumMap.put(TestEnum.PARENT,"培育小孩");

        String result = enumMap.get(TestEnum.PARENT);
        System.out.println(result);
    }

    @org.junit.Test
    public void test4() {
       /* TestEnum[] values = TestEnum.values();
        System.out.println(values);*/

        EnumSet enumSet = EnumSet.allOf(TestEnum.class);
        System.out.println(enumSet);

    }

    private void enumTest() throws Exception {
        Class<?> aClass = Class.forName("xx.xx.xx");
        Constructor<?> constructor = aClass.getDeclaredConstructor(String.class);
        TestEnum singleton = (TestEnum) constructor.newInstance("");
    }
}
