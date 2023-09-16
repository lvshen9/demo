package com.lvshen.demo.java8.sort;

import cn.hutool.core.lang.Console;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2021-12-29 10:24
 * @since JDK 1.8
 */
public class ListSortTest {
    final List<Sportsman> sportsmen = Lists.newArrayList(
            new Sportsman("Lvshen", 180),
            new Sportsman("Zhouzhou", 170),
            new Sportsman("Alan", 190)
    );
    @Test
    public void testString() {
        List<String> stringList = Arrays.asList("Lvshen", "Zhouzhou", "Alan");
        Collections.sort(stringList);
        Console.log(stringList);
    }


    @Test
    public void test1() {
        sportsmen.sort(Comparator.comparing(Sportsman::getName));
        Console.log(sportsmen);
    }

    @Test
    public void test2() {
        sportsmen.add(new Sportsman("Alan", 165));
        Console.log("比较前：【{}】", sportsmen.toString());
        sportsmen.sort(Sportsman::compareByNameThenHeight);
        Console.log("比较后：【{}】", sportsmen.toString());
    }

    @Test
    public void test3() {
        sportsmen.add(new Sportsman("Alan", 165));
        Console.log("比较前：【{}】", sportsmen.toString());
        sportsmen.sort((s1, s2) -> {
            if (s1.getName().equals(s2.getName())) {
                return Integer.compare(s1.getHeight(), s2.getHeight());
            } else {
                return s1.getName().compareTo(s2.getName());
            }
        });

        Console.log("比较后：【{}】", sportsmen.toString());
    }

    @Test
    public void test4() {
        sportsmen.add(new Sportsman("Alan", 165));
        sportsmen.sort(Comparator.comparing(Sportsman::getName).thenComparing(Sportsman::getHeight));
        Console.log("over");
    }

    @Test
    public void test5() {
        final List<Sportsman> sortedSportsman = sportsmen.stream()
                .sorted(Comparator.comparing(Sportsman::getName, Comparator.reverseOrder()))
                .collect(Collectors.toList());

        Console.log("over");

        final List<Sportsman> sortedSportsman2 = sportsmen.stream()
                .sorted(Comparator.comparing(Sportsman::getName).reversed())
                .collect(Collectors.toList());
    }

    @Test
    public void sortedNullLast() {
        final List<Sportsman> sportsmen = Lists.newArrayList(
                null,
                new Sportsman("ghost", 120),
                new Sportsman("god", 300),
                null
        );
        //sportsmen.sort(Comparator.nullsLast(Comparator.comparing(Sportsman::getName)));
        sportsmen.sort(Comparator.nullsFirst(Comparator.comparing(Sportsman::getName)));
        Console.log("test is over");
    }

    @Test
    public void sortedNull() {
        final List<Sportsman> sportsmen = Lists.newArrayList(
                null,
                new Sportsman("ghost", 120),
                null
        );
        sportsmen.stream()
                .sorted(Comparator.comparing(Sportsman::getName).reversed())
                .collect(Collectors.toList());
        Console.log("test is over");
    }






}
