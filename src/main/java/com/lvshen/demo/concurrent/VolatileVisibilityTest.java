package com.lvshen.demo.concurrent;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/4/10 15:24
 * @since JDK 1.8
 */
public class VolatileVisibilityTest {
    //private static volatile boolean initFlag = false;
    private static boolean initFlag = false;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            System.out.println("waiting data");
            while (!initFlag) {
                //System.out.println();
                //里面添加锁共享变量就不会缓存
                synchronized (VolatileVisibilityTest.class) {

                }
            }
            System.out.println("========success!!!");
        }).start();


        Thread.sleep(2000);

        new Thread(() -> prepareData()).start();


    }

    private static void prepareData() {
        System.out.println("开始修改initFlag...");
        initFlag = true;
        System.out.println("initFlag修改成功...");
    }
}
