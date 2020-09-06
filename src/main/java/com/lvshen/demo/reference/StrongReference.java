package com.lvshen.demo.reference;

import org.junit.Test;

/**
 * Description: 强引用
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/9/5 20:09
 * @since JDK 1.8
 */
public class StrongReference {

    @Override
    protected void finalize() throws Throwable {
        System.out.println("StrongReference类被回收了！！！");
    }

    @Test
    public void testStrongReference() {
        StrongReference strongReference = new StrongReference();
        strongReference = null;
        System.gc();
    }
}
