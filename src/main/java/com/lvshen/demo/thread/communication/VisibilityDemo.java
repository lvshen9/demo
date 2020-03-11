package com.lvshen.demo.thread.communication;

import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2020/3/11 22:03
 * @since JDK 1.8
 */
public class VisibilityDemo {
    private static volatile boolean is = true;

    public static void main(String[] args) throws InterruptedException {

        new Thread(() -> {
            int i = 0;
            while (is) {
                i++;
            }
            System.out.println(i);
        }).start();

        TimeUnit.SECONDS.sleep(2);
        is = false;
        System.out.println("is被置为false了");
    }

}
