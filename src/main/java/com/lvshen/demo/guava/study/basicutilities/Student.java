package com.lvshen.demo.guava.study.basicutilities;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;

import lombok.Data;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/6/27 14:37
 * @since JDK 1.8
 */
@Data
public class Student implements Comparable<Student> {
    private String name;
    private int age;
    private int score;
    @Override
    public int compareTo(Student o) {
        return ComparisonChain.start()
                .compare(name,o.name)
                .compare(score, o.score, Ordering.natural().nullsLast()).result();
    }

    public Student(String name, int age, int score) {
        this.name = name;
        this.age = age;
        this.score = score;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Student) {
            Student that = (Student) obj;
            return Objects.equal(name, that.name) && Objects.equal(age, that.age) && Objects.equal(score, that.score);
        }
        return false;
    }

    @Override
    public int hashCode() {

        return Objects.hashCode(super.hashCode(), name, age, score);
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", score=" + score +
                '}';
    }
}
