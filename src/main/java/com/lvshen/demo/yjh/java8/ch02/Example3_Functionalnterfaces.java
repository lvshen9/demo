package com.lvshen.demo.yjh.java8.ch02;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;


/**
 * Predicate是一个布尔类型的函数，该函数只有一个输入参数。Predicate接口包含
 * 了多种默认方法，用于处理复杂的逻辑动词（and,	or，negate）
 */
public class Example3_Functionalnterfaces {

    public static void main(String[] args) {

        Predicate<String> nameStartWithS = name -> name.startsWith("s");
        boolean b = nameStartWithS.test("shen");
        System.out.println("b: " + b);

        Consumer<String> sendEmail = message -> System.out.println("Sending email >> " + message);
        sendEmail.accept("lvshen");

        Function<String, Integer> stringToLength = name -> name.length();

        Supplier<String> uuidSupplier = () -> UUID.randomUUID().toString();
        System.out.println("uuidSupplier: " + uuidSupplier.get());

    }
}
