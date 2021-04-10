package com.lvshen.demo.threadlist;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2021-1-29 16:00
 * @since JDK 1.8
 */
public class ListTest {
    private static List<Integer> list = Lists.newArrayList();

    private static ExecutorService executorService = Executors.newFixedThreadPool(1000);

    private static class IncreaseTask extends Thread{
        @Override
        public void run() {
            System.out.println("ThreadId:" + Thread.currentThread().getId() + " start!");
            for(int i =0; i < 100; i++){
                list.add(i);
            }
            System.out.println("ThreadId:" + Thread.currentThread().getId() + " finished!");
        }
    }

    public static void main(String[] args){
        for(int i=0; i < 1000; i++){
            executorService.submit(new IncreaseTask());
        }
        executorService.shutdown();
        while (!executorService.isTerminated()){
            try {
                Thread.sleep(1000*10);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println("All task finished!");
        System.out.println("list size is :" + list.size());
    }

    @Test
    public void test1() {
        List<String> players = new ArrayList<>(3);
        players.add("1");
        players.add("2");
        players.add("3");
        players.add("4");
        players.add("5");

        System.out.println(players);
    }

    @Test
    public void testList() {
        int[] array = {1, 2, 3};
        List list = Arrays.asList(array);

        System.out.println(list);
        //list.add(4);

        array[2] = 10;
        Object o = list.get(0);
        System.out.println(o);

        Integer[] arrayInteger = {1, 2, 3};
        List listInteger = Arrays.asList(arrayInteger);
        System.out.println(listInteger);

    }

    @Test
    public void testList2() {
        String[] arrayStr = {"1", "2", "3"};
        List<String> list = Arrays.asList(arrayStr);
        System.out.println(list);

        arrayStr[2] = "10";

        System.out.println(list);
    }

    @Test
    public void testList3() {
        String[] arrayStr = {"1", "2", "3"};
        List<String> list = new ArrayList<>();

        list.add("4");
        System.out.println(list);

    }

    @Test
    public void testList4() {
        int[]  a = {1,2,3};
        List list = CollectionUtils.arrayToList(a);
        System.out.println(list);

        List<Integer> iList = Arrays.stream(a)
                .boxed()
                .collect(Collectors.toList());
        System.out.println(iList);
    }
}
