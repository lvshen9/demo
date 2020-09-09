package com.lvshen.demo.reference;

import com.lvshen.demo.extendsTest.Student;
import org.junit.Test;

import java.lang.ref.SoftReference;

/**
 * Description: 软引用
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/9/5 20:27
 * @since JDK 1.8
 */
public class SoftReferenceTest {

    @Test
    public void test1() {
        SoftReference<Student> studentSoftReference=new SoftReference<Student>(new Student());
        Student student = studentSoftReference.get();
        System.out.println(student);
    }

    @Test
    public void test2() {
        SoftReference<byte[]> softReference = new SoftReference<byte[]>(new byte[1024*1024*10]);
        System.out.println(softReference.get());
        System.gc();
        System.out.println(softReference.get());
        byte[] bytes = new byte[1024 * 1024 * 10];
        System.out.println(softReference.get());
    }



}
