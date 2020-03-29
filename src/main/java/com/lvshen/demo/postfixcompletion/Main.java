package com.lvshen.demo.postfixcompletion;

import com.lvshen.demo.member.entity.Member;
import com.lvshen.demo.treenode.Student;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: Postfix Completion 使用
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/3/14 12:53
 * @since JDK 1.8
 */
public class Main {

    private static Student student;

    public static void main(String[] args){

        Student student = new Student();//.var
        Main.student = new Student();// .filed 全局变量
        Student student1 = new Student(); //Student.new
        Student student2 = (Student) new Object(); //new Object().castvar

        if (student == null) {  //student.null

        }

        if (student != null) { //student.notnull

        }

        boolean flag = true;
        if (flag) {   //flag.if

        }

        while (flag) { //flag.while

        }

        System.out.println(flag); //flag.sout
        //return flag;     flag.return

        String[] strs = new String[5];
        for (int i = 0; i < strs.length; i++) { //strs.fori

        }

        for (String str : strs) {  //strs.for

        }

        for (int i = strs.length - 1; i >= 0; i--) { //strs.forr

        }

        try {
            main(new String[]{});  //main.try
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Member> members =new ArrayList<>(); //Member.list
    }

    @Test
    public void test(){
        
    }
}
