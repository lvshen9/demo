package com.lvshen.demo.test;

import com.google.common.collect.Lists;
import com.lvshen.demo.member.entity.Member;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/6/3 16:49
 * @since JDK 1.8
 */
public class Java8Test {

    public List<Member> listMember() {
        List<Member> members = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            Member member = new Member();
            member.setId("M" + i);
            member.setCode(100 + i);
            member.setName("Lvshen" + i);

            members.add(member);
        }
        return members;

    }

    @Test
    public void test1() {
        List<Member> members = listMember();
        System.out.println(members);

        //获取ids
        List<String> ids = members.stream().map(Member::getId).collect(Collectors.toList());
        System.out.println(ids);

        //条件过滤
        List<Member> filterMembers = members.stream().filter(x -> x.getCode() > 107).collect(Collectors.toList());
        System.out.println(filterMembers);

        //非空判断 获取第一条
        Member firstNotNUllMember = Optional.ofNullable(members).orElseGet(ArrayList::new).stream().findFirst().get();
        System.out.println(firstNotNUllMember);
        //list转map
        Map<String, Member> memberMap = members.stream().collect(Collectors.toMap(Member::getId, value -> value, (k1, k2) -> k1));
        System.out.println("list转map" + memberMap);

        //map遍历1
        memberMap.forEach((id, member) -> {
            System.out.println("key=" + id +":"+ "value=" + member);
        });

        //map遍历2
        memberMap.entrySet().stream().forEach(entry -> {
            System.out.println("entry遍历" + entry.getKey());
            System.out.println("entry遍历" + entry.getValue());
        });

        //分组统计
        Map<String, Long> countMap = members.stream().collect(Collectors.groupingBy(Member::getName, Collectors.counting()));
        System.out.println("分组统计：" + countMap);

        //循环遍历1
        members.forEach(x -> {
            System.out.println("forEach_code:" + x.getCode());
            System.out.println("forEach_name:" + x.getName());
            System.out.println();
        });

        //倒序排序
        List<Member> reversSortedMembers = members.stream().sorted(Comparator.comparing(Member::getCode).reversed()).collect(Collectors.toList());
        System.out.println("倒序排序:" + reversSortedMembers);

        //集合添加
        List<List<Member>> collect = Stream.of(members).collect(Collectors.toList());
        System.out.println(collect);

        //memberMap.clear();

        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        numbers.stream().distinct().forEach(System.out::println);


    }
}
