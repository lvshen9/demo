package com.lvshen.demo.thread.future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/4/21 12:44
 * @since JDK 1.8
 */
@Slf4j
public class Test {

    public static void main(String[] args) {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                log.info("当前线程：{}", Thread.currentThread().getName());
                return "Lvshen";
            }
        };

        MyFutureTask<String> myFutureTask = new MyFutureTask(callable);
        new Thread(myFutureTask).start();

        System.out.println(String.format("当前线程：[%s],取出的值：[%s]", Thread.currentThread().getName(), myFutureTask.get()));
    }
}
