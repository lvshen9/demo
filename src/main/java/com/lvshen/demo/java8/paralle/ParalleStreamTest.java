package com.lvshen.demo.java8.paralle;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lvshen.demo.treenode.Student;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/6/28 16:35
 * @since JDK 1.8
 */
public class ParalleStreamTest {

	private static List<Integer> list1 = new ArrayList<>();
	private static List<Integer> list2 = new ArrayList<>();
	private static List<Integer> list3 = new ArrayList<>();
	private static Lock lock = new ReentrantLock();


    public static ThreadLocal<String> threadLocal = new ThreadLocal<>();

	public static void main(String[] args) {
		IntStream.range(0, 10000).forEach(list1::add);
		IntStream.range(0, 10000).parallel().forEach(list2::add);

		IntStream.range(0, 10000).forEach(i -> {
			lock.lock();
			try {
				list3.add(i);
			} finally {
				lock.unlock();
			}
		});

        System.out.println("串行执行的大小：" + list1.size());
        System.out.println("并行执行的大小：" + list2.size());
        System.out.println("加锁并行执行的大小：" + list3.size());
	}

	@Test
	public void test(){
        testDate();
    }

    private static void testDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Calendar> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Calendar startDay = new GregorianCalendar();
            Calendar checkDay = new GregorianCalendar();
            checkDay.setTime(startDay.getTime());//不污染入参
            checkDay.add(Calendar.DATE,i);
            list.add(checkDay);
            checkDay = null;
            startDay = null;
        }

        list.stream().forEach(day ->  System.out.println(sdf.format(day.getTime())));
        System.out.println("-----------------------");
        list.parallelStream().forEach(day ->  System.out.println(sdf.format(day.getTime())));
        List<String> collect = list.parallelStream().map(day -> sdf.format(day.getTime())).collect(toList());
        System.out.println(collect);
        System.out.println("-----------------------");
    }

    @Test
    public void test1(){
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }
        list.stream().forEach(System.out::println);
        System.out.println("-----------------------");

        list.parallelStream().forEach(System.out::println);
    }

    @Test
    public void test2() {
	    Map<String,String> map = Maps.newHashMap();
	    map.put("A","apache");
	    map.put("B","Bob");
        System.out.println(JSON.toJSONString(map));
    }

    @Test
    public void test3() {
        Student student = new Student();
        List<Student> students = Lists.newArrayList();
        student.setName("小1");
        students.add(student);
        student.setName("小2");
        students.add(student);

        System.out.println(students);
    }
    @Test
    public void test4() {
	    String flag = "1";
	    threadLocal.set(flag);
        long l = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            String a = String.format("%s_%s", i, i);
            //System.out.println("a:" + a);
        }
        System.out.println("1:" + (System.currentTimeMillis() - l));
        flag = "0";
    }

    @Test
    public void test5() {
        String a = "1";
        String b = "2";
        if ("1".equals(a)) {
            System.out.println(1);
        } else if ("2".equals(b)) {
            System.out.println(2);
        }
    }

}
