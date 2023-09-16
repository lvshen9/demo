package com.lvshen.demo.java8.stream;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lvshen.demo.common.MyStringUtils;
import com.lvshen.demo.member.entity.Member;
import com.lvshen.demo.member.service.MemberService;
import com.lvshen.demo.treenode.Student;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-8-25 15:23
 * @since JDK 1.8
 */
public class OptionalTest {
    private MemberService memberService = new MemberService();

    public void createMembers(Student student) {
        if (student != null) {
            String name = student.getName();
            if (StringUtils.isNotEmpty(name)) {
                List<Member> members = memberService.listByName(name);
                if (CollectionUtils.isNotEmpty(members)) {
                    members.forEach(m -> {
                        m.setCode(student.getScore());
                        memberService.createMember(m);
                    });
                }
            }
        }
    }

    public void createMembersNew(Student student) {
        if (student == null) {
            return;
        }
        String name = student.getName();
        if (name == null) {
            return;
        }
        List<Member> members = memberService.listByName(name);
        if (CollectionUtils.isNotEmpty(members)) {
            members.forEach(m -> {
                m.setCode(student.getScore());
                memberService.createMember(m);
            });
        }
    }

    public void createMembersFinal(Student student) {
        Optional<Student> studentOpt = Optional.ofNullable(student);
        String name = studentOpt.map(Student::getName).orElseGet(String::new);
        List<Member> members = listByName(name);
        Optional.ofNullable(members).orElseGet(ArrayList::new).forEach(m -> {
            m.setCode(student.getScore());
            //memberService.createMember(m);
        });
        System.out.println(members);
    }

    @Test
    public void test() {
        Student student = null;
        createMembersFinal(student);
    }

    private List<Member> listByName(String name) {
        if (StringUtils.isBlank(name)) {
            return ImmutableList.of();
        }
        List<Member> members = Lists.newArrayList();
        members.forEach(x -> {
            x.setCode(1);
            x.setId("1");
            x.setName("Lvshen");
        });
        return members;
    }

    private List<Member> listMember() {
        return Stream.of(
                new Member("1", "Lvshen", 10, "订阅号：Lvshen的技术小屋"),
                new Member("2", "Lvshen2", 20, "头条号：Lvshen的技术小屋"),
                new Member("3", "Lvshen3", 30, "知乎：Lvshen"),
                new Member("4", "Lvshen4", 10, "CSDN：Lvshen")
        ).collect(Collectors.toList());
    }

    private List<Member> listMemberForOld() {
        List<Member> list = new ArrayList<>();
        Member member1 = new Member("1", "Lvshen", 10, "订阅号：Lvshen的技术小屋");
        Member member2 = new Member("2", "Lvshen2", 20, "头条号：Lvshen的技术小屋");
        Member member3 = new Member("3", "Lvshen3", 30, "知乎：Lvshen");
        Member member4 = new Member("4", "Lvshen4", 10, "CSDN：Lvshen");

        list.add(member1);
        list.add(member2);
        list.add(member3);
        list.add(member4);

        return list;
    }

    //分组
    @Test
    public void testGroupBy() {
        List<Member> memberList = listMember();

        Map<Integer, List<Member>> map = memberList
                .stream().collect(Collectors.groupingBy(Member::getCode));
        System.out.println(map);
    }

    //将List按对应属性转成Map
    @Test
    public void testMap() {
        List<Member> memberList = listMember();
        Map<String, Member> memberMap = memberList
                .stream().collect(Collectors.toMap(Member::getId, value -> value, (key1, key2) -> key1));

        System.out.println(memberMap);
    }

    @Test
    public void testRemove() {
        List<Member> memberList = listMember();
        Iterator<Member> iterator = memberList.iterator();
        while (iterator.hasNext()) {
            Member next = iterator.next();
            if (10 == next.getCode()) {
                iterator.remove();
            }
        }
        System.out.println(memberList);

        for (Member member : memberList) {

        }
    }

    //过滤来代替删除
    @Test
    public void testFilter() {
        List<Member> memberList = listMember();
        List<Member> memberListAfterFilter = memberList.stream().filter(member -> 10 != member.getCode()).collect(Collectors.toList());
        System.out.println(memberListAfterFilter);
    }

    //求和
    @Test
    public void testSum() {
        List<Member> memberList = listMember();
        int sum = memberList.stream().mapToInt(Member::getCode).sum();
        System.out.println(sum);

        //如果是BigDecimal
        //BigDecimal totalMoney = appleList.stream().map(Apple::getMoney).reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    //最值
    @Test
    public void testMax() {
        List<Member> memberList = listMember();
        Optional<Member> maxMemberOpt = memberList.stream().collect(Collectors.maxBy(Comparator.comparing(Member::getCode)));
        maxMemberOpt.ifPresent(System.out::println);
    }

    //去重
    @Test
    public void testDistinct() {
        //跟据特定的属性去重
        List<Member> memberList = listMember();
        List<Integer> codeList = memberList.stream().map(Member::getCode).collect(Collectors.toList());

        List<Integer> afterDistinctList = codeList.stream().distinct().collect(Collectors.toList());
        System.out.println(afterDistinctList);
    }

    //排序
    @Test
    public void testSort() {
        List<Member> memberList = listMember();
        //按code排序
        List<Member> reversedList = memberList.stream().sorted(Comparator.comparing(Member::getCode).reversed()).collect(Collectors.toList());
        System.out.println(reversedList);
    }

    //统计
    @Test
    public void testCount() {
        List<Member> memberList = listMember();
        Map<Integer, Long> countMap = memberList.stream().collect(Collectors.groupingBy(Member::getCode, Collectors.counting()));
        System.out.println(countMap);
    }

    @Test
    public void testOperatorMap() {
        List<Member> memberList = listMember();
        Map<String, Member> memberMap = memberList
                .stream().collect(Collectors.toMap(Member::getId, value -> value, (key1, key2) -> key1));

        //原始操作
        Set<Map.Entry<String, Member>> entries = memberMap.entrySet();
        for (Map.Entry<String, Member> entry : entries) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }

        System.out.println();

        //Java8
        memberMap.forEach((key, value) -> System.out.println(key + ":" + value));

    }

    @Test
    public void testOperatorMap2() {
        Map<String, String> map = Maps.newHashMap();
        map.put("key", "微信搜：Lvshen_9");
        if (map.get("key") == null) {
            map.put("key", "Lvshen的技术小屋");
        }

        map.putIfAbsent("key", "Lvshen的技术小屋");

        System.out.println(map);
    }

    @Test
    public void testMap4Java8() {
        List<String> list = Lists.newArrayList();
        list.add("1");
        list.add("2");
        list.add("3");

        list = list.stream().map(x -> "'".concat(x).concat("'")).collect(Collectors.toList());

        String s = MyStringUtils.list2String(list, ",");
        System.out.println(s);
    }

}
