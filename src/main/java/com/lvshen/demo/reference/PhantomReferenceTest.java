package com.lvshen.demo.reference;

import com.lvshen.demo.extendsTest.Student;
import org.junit.Test;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * Description: 虚引用
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/9/5 20:37
 * @since JDK 1.8
 */
public class PhantomReferenceTest {

    @Test
    public void test() {

        ReferenceQueue queue = new ReferenceQueue();
        PhantomReference<Student> reference = new PhantomReference<Student>(new Student(),queue);
        //System.gc();
        Student student = reference.get();
        System.out.println(student);

    }
}
