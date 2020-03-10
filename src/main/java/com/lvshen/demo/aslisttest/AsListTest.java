package com.lvshen.demo.aslisttest;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/9/4 17:36
 * @since JDK 1.8
 */
public class AsListTest {

    @Test
    public void test1() {
        List<Integer> list = Arrays.asList(1,2,3);
        list.add(5);
        System.out.print(list.toString());
    }

    @Test
    public void test2() {
        List<Integer> list = new ArrayList<>(Arrays.asList(1,2,3));
        list.add(5);
        System.out.println(list.toString());
    }

    @Test
    public void test3() {
        Function<String, String> fun1= x -> x + 1;

        //Function<String, String> fun1 = Function.identity();
        String str1 = fun1.apply("helloWorld");
        System.out.println(str1);

    }

    @Test
    public void test4() {
        List<String> list = Collections.unmodifiableList(null);
        System.out.println(list);
    }
}
