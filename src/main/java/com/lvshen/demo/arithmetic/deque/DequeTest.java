package com.lvshen.demo.arithmetic.deque;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/12/20 9:38
 * @since JDK 1.8
 */
@Slf4j
public class DequeTest {

    @Test
    public void test1() {
        Deque<String> arrayDeque = new ArrayDeque<>();
        //arrayDeque.addLast("t1");
        arrayDeque.addFirst("t2");
        arrayDeque.addFirst("t3");
        arrayDeque.addFirst("t4");
        arrayDeque.addFirst("t5");
        //arrayDeque.addLast("t6");

        log.info(arrayDeque.toString());
        //String pop = arrayDeque.pop();
        String removeLast = arrayDeque.removeLast();
        log.info(arrayDeque.toString());
        log.info(removeLast);
    }

    @Test
    public void test() {
        String str = "123";
        String str2 = new String("123");
        String str3 = str2.intern();
        String str4 = str.intern();

        System.out.println(str == str2);
        System.out.println(str == str3);
        System.out.println(str2 == str3);

        System.out.println("-------------------------");
        System.out.println(str == str4);
        System.out.println(str2 == str4);
        System.out.println(str3 == str4);


    }

}
