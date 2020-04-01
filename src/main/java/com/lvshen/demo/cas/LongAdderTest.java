package com.lvshen.demo.cas;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/3/29 11:48
 * @since JDK 1.8
 */
public class LongAdderTest {
    private static ExecutorService service;

    //从初始值0开始，做加/减处理，每次加/减1
    private static LongAdder adder = new LongAdder();

    //从初始值0开始，做累加处理
    private static LongAccumulator accumulator1 = new LongAccumulator((x, y) -> x + y, 0);

    //从初始值1开始，做累积处理
    private static LongAccumulator accumulator2 = new LongAccumulator((x, y) -> x * y, 1);

    public static void main(String[] args) {
        service = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {

            service.submit(() -> {
                adder.increment(); //加1
                //          adder.decrement(); //减1
                System.out.println(adder.intValue());
                /*accumulator1.accumulate(2); //每次加2
                System.out.println("accumulator1: " + accumulator1.get()); //获取当前值

                accumulator2.accumulate(2); //每次 * 2
                System.out.println(" accumulator2:" + accumulator2.get()); //获取当前值*/

            });
        }

        service.shutdown();
    }
}
