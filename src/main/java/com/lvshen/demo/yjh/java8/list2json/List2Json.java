package com.lvshen.demo.yjh.java8.list2json;

import com.alibaba.fastjson.JSON;
import com.lvshen.demo.treenode.Student;
import org.junit.Test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/8/6 15:34
 * @since JDK 1.8
 */
public class List2Json {

    @Test
    public void test() {
        List<Student> list = new ArrayList<>();
        list.add(new Student(10,"xiaoming"));
        list.add(new Student(5,"daniu"));
        list.add(new Student(6,"alan"));
        list.add(new Student(4,"hello"));

        System.out.println("转Json之前：" + list.toString());
        String s = JSON.toJSONString(list);
        //String s = JsonUtils.toJsonString(list);
        System.out.println("转Json之后：" + s);
    }

    @Test
    public void test1() {
        List<String> asList = Arrays.asList("xiaoming");
        List<String> singletonList = Collections.singletonList("xiaoming");
        System.out.println("asList:" + asList);
        System.out.println("singletonList:" + singletonList);

    }

    @Test
    public void test2() {
        List<String> list = new ArrayList<>();
        list.add(null);
        list.add("1");
        list.add("2");
        System.out.println(list);

        list.removeAll(Collections.singleton(null));
        System.out.println(list);
    }
}
