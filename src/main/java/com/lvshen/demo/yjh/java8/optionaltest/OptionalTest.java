package com.lvshen.demo.yjh.java8.optionaltest;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/7/8 8:50
 * @since JDK 1.8
 */
public class OptionalTest {

    @Test
    public void test() {
        List<Double> a = new ArrayList<>();
        Optional<List<Double>> one1 = Optional.of(a);
        System.out.println(one1.isPresent());
        // 没有达到我预想的效果
        if (one1.isPresent()) {
            double b = one1.get().stream().mapToDouble(x -> x).average().getAsDouble();
        }
    }
}
