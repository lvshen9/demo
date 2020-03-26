package com.lvshen.demo;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.lvshen.demo.treenode.Student;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/4/28 20:45
 * @since JDK 1.8
 */
@Slf4j
public class MyTest {

    Lock lock = new ReentrantLock();

    @Test
    public void test1() {
        int i = 1;
        List<Integer> integers = Arrays.asList(i);
        for (Integer t : integers) {
            System.out.println(t);
        }
        System.out.println(integers);
    }

    @Test
    public void test2() {
        List<Student> list1 = new ArrayList<>();
        list1.add(new Student(90, "xiaoming"));
        list1.add(new Student(80, "xiaoming"));
        list1.add(new Student(70, "daniu"));
        // 求和
        int sum = list1.stream().mapToInt(Student::getScore).sum();
        System.out.println(sum);
        System.out.println("---------list转map------------");
        Map<Integer, Student> collect = list1.stream()
                .collect(Collectors.toMap(Student::getScore, a -> a, (k1, k2) -> k1));
        System.out.println(collect);
    }

    @Test
    public void test3() {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "lvshen");
        map.put(2, "humulan");
        map.put(3, "dujie");
        // map转list
        List<String> list = map.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
        System.out.println(list);
        System.out.println("-----Java8遍历-------");
        list.forEach(System.out::println);
        System.out.println("---------map转list(存在对象中)------------");
        List<Student> collect = map.entrySet().stream().map(e -> new Student(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
        collect.forEach(System.out::println);
    }

    @Test
    public void test4() {
        String nanoTime = String.valueOf(System.nanoTime());
        System.out.println(nanoTime);
        System.out.println(nanoTime.length() - 8);
        Integer integer = Integer.valueOf(nanoTime.substring(nanoTime.length() - 8));
        System.out.println(integer);

    }

    @Test
    public void test5() {
        String s = "菜鸟教程：";
        s = s.concat("www.runoob.com").concat("/org");

        System.out.println(s);
    }

    @Test
    public void test6() {
        // 字符串型
        BigDecimal money = new BigDecimal(12.99);

        // 转换int
        int moneys1 = money.intValue();

        System.out.println(moneys1);

    }

    @Test
    public void test7() {
        List<Student> list = new ArrayList<>();
        List<Student> list1 = new ArrayList<>();
        List<Student> list2 = new ArrayList<>();
        List<Student> list3 = new ArrayList<>();
        list.add(new Student(90, "xiaoming"));
        list.add(new Student(80, "xiaoming"));
        list.add(new Student(70, "daniu"));

        list.forEach(x -> {
            System.out.println(x);
        });

        System.out.println("========================");

        list.stream().forEach(x -> {
            System.out.println(x);
        });

    }

    @Test
    public void testFor() {
        String text = "lvshen";
        int length = 3;
        for (int i = 0; i < text.length(); i++) {
            i = i + length - 1; // 减1的原因，是因为for会自增
        }
    }

    @Test
    public void test8() {
        String ss = "Hello";
        String[] bb = {"H", "e", "l", "l", "o"};
        String[] strings = {"Hello", "World"};

        List<Stream<String>> collect = Arrays.asList(strings).stream().map(str -> str.split(""))
                .map(str -> Arrays.stream(str)).collect(Collectors.toList());

        List<String[]> collect1 = Arrays.asList(strings).stream().map(str -> str.split("")).distinct()
                .collect(Collectors.toList());

        List<String> collect2 = Arrays.asList(strings).stream().map(str -> str.split(""))
                .flatMap(strings1 -> Arrays.stream(strings1)).collect(Collectors.toList());
        System.out.println(collect2);
    }

    @Test
    public void whenEmptyValue_thenReturnDefault() {
        Student user = null;
        Student user2 = new Student(90, "xiaoming");
        Student result = Optional.ofNullable(user).orElse(user2);

        System.out.println(user2.getScore() == result.getScore());

    }

    @Test
    public void getCookie() {
        List<Cookie> cookies = Lists.newArrayList();
        Cookie sessionCookie = new BasicClientCookie("jsessionid", "https://kany.me");
        cookies.add(sessionCookie);
        Cookie sessionSameCookie = new BasicClientCookie("jsessionid", "https://www.kany.me");
        cookies.add(sessionSameCookie);
        Cookie otherCookie = new BasicClientCookie("other", "http://www.kany.me");
        cookies.add(otherCookie);

        List<Cookie> targetCookies = Optional.ofNullable(cookies).orElseGet(ArrayList::new).stream()
                .filter(e -> "jsessionid".equalsIgnoreCase(e.getName())).collect(Collectors.toList());

        log.info("Cookie名称为：{}的值为:{}", "jsessionid",
                Optional.ofNullable(targetCookies).orElseGet(ArrayList::new).get(0).getValue());

        Optional<Cookie> first = Optional.ofNullable(cookies).orElseGet(ArrayList::new).stream()
                .filter(e -> "jsessionid".equalsIgnoreCase(e.getName())).findFirst();
        if (first.isPresent()) {
            log.info("Cookie名称为：{}的值为:{}", "jsessionid", first.get().getValue());
        }

        Optional<Cookie> any = Optional.ofNullable(cookies).orElseGet(ArrayList::new).stream()
                .filter(e -> "jsessionid".equalsIgnoreCase(e.getName())).findAny();

        if (any.isPresent()) {
            log.info("Cookie名称为：{}的值为:{}", "jsessionid", any.get().getValue());
        }
    }

    @Test
    public void testPair() {
        Pair<String, String> pair = new Pair<>("aku", "female");

        log.info("key为:{}，value为:{}", pair.getKey(), pair.getValue());

        org.apache.commons.lang3.tuple.Pair<String, String> of = org.apache.commons.lang3.tuple.Pair.of("aku",
                "female");
        log.info("lef为:{}，right为:{}", of.getLeft(), of.getRight());

        MutablePair<String, String> mp = MutablePair.<String, String>of("s", "s");
        log.info("MutablePair测试:{},{}", mp.getLeft(), mp.getRight());
    }

    @Test
    public void testCollectionUtils() {
        String[] arrayA = new String[]{"A", "B", "C", "D", "E", "F"};
        String[] arrayB = new String[]{"B", "D", "F", "G", "H", "K"};

        List<String> listA = Arrays.asList(arrayA);
        List<String> listB = Arrays.asList(arrayB);

        // 集合A,B取并集
        String s = ArrayUtils.toString(CollectionUtils.union(listA, listB));
        log.info("取得并集：{}", s);

    }

    @Test
    public void testUnModifiableCollection() {
        Collection<String> c = new ArrayList<>();

        Collection collection = CollectionUtils.unmodifiableCollection(c);
        c.add("boy");
        c.add("love");
        c.add("girl");

        // collection.add("a");

        System.out.println(collection);

    }

    @Test
    public void testReduce() {
        Integer[] integers = {1, 2, 3, 4, 5, 6, 7, 8};
        Stream<Integer> stream = Arrays.stream(integers);

        // 元素求和
        Integer sum = stream.reduce(0, Integer::sum);
        System.out.println("sum:" + sum);

        boolean present = stream.reduce((i, j) -> i + j).isPresent();

    }

    @Test
    public void testImmutableList() {
        int a = 23;
        ImmutableList<Integer> list = ImmutableList.of(a, 12);
        System.out.println(list);
        a = 232;
        System.out.println(list);

        List<String> list1 = new ArrayList<>();
        list1.add("1");
        list1.add("2");
        list1.add("3");

        List<String> list2 = ImmutableList.<String>copyOf(list1);
        System.out.println("list2:" + list2);

    }

    @Test
    public void testConstantList() {
        List<String> constantList = new ImmutableList.Builder<String>().add("平均值").add("总值").add("最大值").add("最小值")
                .build();

        ImmutableList<String> list = ImmutableList.of("平均值");
        list.add("ss");

        // constantList.add("ss");

    }

    @Test
    public void testForEach() {
        List<String> list = Arrays.asList("xiaoming", "aniu");
        list.forEach(this::simple);
        System.out.println("===================");
        list.forEach(x -> simple(x));
    }

    public void simple(String str) {
        System.out.println(str);
    }

    @Test
    public void testForEach2() {
        List<String> list = Arrays.asList("xiaoming", "aniu");
        List<Student> collect = list.stream().map(Student::new).collect(Collectors.toList());
        System.out.println(collect);
        System.out.println("===================");
        List<Student> collect2 = list.stream().map(x -> new Student(x)).collect(Collectors.toList());
        System.out.println(collect2);
    }

    @Test
    public void testFor2() {
        log.info("循环开始");
        outer:
        for (int i = 0; i < 11; i++) {
            log.info("外层循环");
            for (int j = 0; j < 5; j++) {

                if (j == 4) {
                    break;
                }

            }
        }
        log.info("循环结束");
    }

    Object o = new Object();
    @Test
    public void testBuilder() {
        int a = 10;
        synchronized (o) {
            while (a > 0) {
                --a;
            }
        }
        System.out.println(a);
    }


    @Test
    public void testBuilder2() {
        int a = 10;
        lock.lock();
        try {
			while (a > 0) {
				--a;
			}

		} finally {
			lock.unlock();
		}
        System.out.println(a);
    }


}
