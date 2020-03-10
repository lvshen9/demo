package com.lvshen.demo.treenode;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/5/9 8:47
 * @since JDK 1.8
 */
public class Student {
    private Integer score;
    private String name;
    public Student() {
    }

    @Override
    public String toString() {
        return "Student{" +
                "score=" + score +
                ", name='" + name + '\'' +
                '}';
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Student(Integer score, String name) {
        this.score = score;
        this.name = name;
    }

    public Student(String name) {
        this.name = name;
    }
}
