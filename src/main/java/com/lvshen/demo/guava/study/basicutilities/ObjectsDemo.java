package com.lvshen.demo.guava.study.basicutilities;

import org.junit.Test;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/6/27 14:43
 * @since JDK 1.8
 */
public class ObjectsDemo {
    @Test
    public void test1(){
        //Objects.equal,不用担心空指针异常
        System.out.println(Objects.equal("a", "a"));
        System.out.println(Objects.equal(null, "a"));
        System.out.println(Objects.equal("a", null));
        System.out.println(Objects.equal(null, null));
    }

    @Test
    public void toStringTest() {
        System.out.println(MoreObjects.toStringHelper(ObjectsDemo.class).add("x", 1).toString());
        System.out.println(MoreObjects.toStringHelper(Employee.class).add("x", 1).toString());

        Employee emp = new Employee("zhang", 18);
        String result = MoreObjects.toStringHelper(Employee.class).add("name", emp.getName()).add("age", emp.getAge())
                .toString();
        System.out.print(result);
    }

    @Test
    public void test3() {
        Student student = new Student("zhang", 23, 80);
        Student student1 = new Student("li", 23, 36);
        Student student2 = new Student("wang", 24, 90);
        Student student3 = new Student("zhang", 23, 80);

        System.out.println("==========equals===========");
        System.out.println(student.equals(student1));
        System.out.println(student.equals(student2));
        System.out.println(student.equals(student3));

        System.out.println("==========hashCode===========");
        System.out.println(student.hashCode());
        System.out.println(student1.hashCode());
        System.out.println(student2.hashCode());
        System.out.println(student3.hashCode());

        System.out.println("==========toString===========");
        System.out.println(student.toString());
        System.out.println(student1.toString());
        System.out.println(student2.toString());
        System.out.println(student3.toString());

        System.out.println("==========compareTo===========");
        System.out.println(student.compareTo(student1));
        System.out.println(student.compareTo(student2));
        System.out.println(student1.compareTo(student));
        System.out.println(student.compareTo(student3));
    }
}
