package com.lvshen.demo.cas;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.LongAdder;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/3/29 11:17
 * @since JDK 1.8
 */
public class MyAtomicInteger {
    private volatile int value;

    private static long offset;//偏移地址

    private static Unsafe unsafe;

    static {
        try {
            Field theUnsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafeField.setAccessible(true);
            unsafe = (Unsafe) theUnsafeField.get(null);
            Field field = MyAtomicInteger.class.getDeclaredField("value");
            offset = unsafe.objectFieldOffset(field);//获得偏移地址
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void increment(int num) {
        int tempValue;
        do {
            tempValue = unsafe.getIntVolatile(this, offset);//拿到值
        } while (!unsafe.compareAndSwapInt(this, offset, tempValue, value + num));//CAS自旋
    }

    public int get() {
        return value;
    }
}
