package com.lvshen.demo.memoryleak;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/8/24 21:04
 * @since JDK 1.8
 */
public class MemoryLeakService {

    public List<User> distinct() throws InterruptedException {
        List<User> list = new ArrayList<>();
        CountDownLatch downLatch = new CountDownLatch(1);
        new Thread(() -> {
            try {
                for (int j = 0; j < 100; j++) {
                    for (int i = 0; i < 100000; i++) {
                        User user = new User(String.valueOf(i));

                        if (!list.contains(user)) {
                             list.add(user);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                downLatch.countDown();
            }
        }).start();
        downLatch.await();
        return list;
    }
    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread());
        new Thread().start();
        new MemoryLeakService().distinct();
    }
}
