package com.lvshen.demo.annotation.validator;

import com.lvshen.demo.treenode.Student;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2021-5-20 17:39
 * @since JDK 1.8
 */
@Component
public class GeneratorStudentService {

    public Student initData() {
        Student student = new Student();
        student.setDate(new Date());
        student.setScore(96);
        student.setName("Lvshen");
        return student;
    }

    @ValidatorHandler(validators = StudentValidator.class)
    public void creatStudent(Student student) {
        System.out.println("开始新增");
        System.out.println("新增结束");
    }
}
