package com.lvshen.demo.threadlocal;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/4/16 10:44
 * @since JDK 1.8
 */
public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable c = () -> {
            String data = "Lvshen";
            System.out.println(Thread.currentThread().getName() + ":" + data);

            return data;
        };

        FutureTask f = new FutureTask<>(c);
        Thread thread = new Thread(f);
        thread.start();

        System.out.println(Thread.currentThread().getName() + ":" + f.get());

    }
}
