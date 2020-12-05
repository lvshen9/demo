package com.lvshen.demo.queue.delayqueue;

import com.lvshen.demo.treenode.Student;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-12-2 8:48
 * @since JDK 1.8
 */
public class PriorityQueueTest {

    public static void main(String[] args) {
        PriorityQueue queue = new PriorityQueue(10, (Comparator<Student>) (v1, v2) -> {
            // 设置优先级规则（倒序，分值越高权限越大）
            return v2.getScore() - v1.getScore();
        });
        // 构建实体类
        Student s1 = new Student(80, "Lvshen", null);
        Student s2 = new Student(100, "Zhouzhou", null);
        Student s3 = new Student(60, "Hall", null);
        // 入列
        queue.offer(s1);
        queue.offer(s2);
        queue.offer(s3);
        while (!queue.isEmpty()) {
            // 遍历名称
            Student item = (Student) queue.poll();
            System.out.println("Name：" + item.getName() +
                    " Level：" + item.getScore());
        }
    }
}
