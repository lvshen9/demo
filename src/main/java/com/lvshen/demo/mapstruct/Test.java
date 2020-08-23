package com.lvshen.demo.mapstruct;

import org.springframework.beans.BeanUtils;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-8-21 16:23
 * @since JDK 1.8
 */
public class Test {
    public StudentVo initVo() {
        StudentVo studentVo = new StudentVo();
        studentVo.setId("1");
        studentVo.setAge("27");
        studentVo.setName("Lvshen");
        studentVo.setCode("001");
        studentVo.setScore("100");
        studentVo.setSex("male");
        return studentVo;
    }

    @org.junit.Test
    public void test1() {
        StudentVo studentVo = initVo();
        StudentDto studentDto = StudentConverter.INSTANCE.vo2dto(studentVo);

        System.out.println(studentDto);
    }

    @org.junit.Test
    public void test2() {
        StudentVo studentVo = initVo();
        StudentDto studentDto = new StudentDto();
        BeanUtils.copyProperties(studentVo,studentDto);

        System.out.println(studentDto);
    }
}
