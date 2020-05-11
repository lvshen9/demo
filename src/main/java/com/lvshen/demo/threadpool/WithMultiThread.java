package com.lvshen.demo.threadpool;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.*;

/**
 * Description:多线程使用示例
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/5/11 13:27
 * @since JDK 1.8
 */
public class WithMultiThread {
    public static void main(String[] args){
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        long startTime = System.currentTimeMillis();

        List<Callable<String>> callableList = Lists.newArrayList();


        Callable<String> callableA = () -> {Thread.sleep(100L); return "A";};
        callableList.add(callableA);

        Callable<String> callableB = () -> {Thread.sleep(200L);return "B";};
        callableList.add(callableB);

        Callable<String> callableC = () -> {Thread.sleep(100L);return "C";};
        callableList.add(callableC);

        StringBuilder stringBuilder = new StringBuilder();//线程不安全

        try {
            List<Future<String>> futures = executorService.invokeAll(callableList);
            futures.forEach( stringFuture -> {
                try {
                    stringBuilder.append(stringFuture.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            });
            long endTime = System.currentTimeMillis();
            System.out.println("多线程执行3个接口，返回：" + stringBuilder.toString() +","+ "总耗时： "+ (endTime - startTime) + "毫秒");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }

    }
}
