package com.lvshen.demo.mapstruct;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public List<StudentVo> initVoList() {
        return Stream.of(
                new StudentVo("1", "21", "Lvshen1", "001","100","male"),
                new StudentVo("2", "22", "Lvshen2", "002","100","male"),
                new StudentVo("3", "23", "Lvshen3", "003","100","male"),
                new StudentVo("4", "24", "Lvshen4", "004","100","male")
        ).collect(Collectors.toList());

    }
    @org.junit.Test
    public void test() {
        List<StudentVo> voList = initVoList();
        List<StudentDto> studentDtos = StudentConverter.INSTANCE.listVo2dto(voList);
        System.out.println(studentDtos);
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

    @org.junit.Test
    public void test3() {
        StudentVo studentVo = initVo();
        StudentDto studentDto = new StudentDto();
        studentDto.setCode(studentVo.getCode());
        studentDto.setId(studentVo.getId());
        studentDto.setSex(studentVo.getSex());
        studentDto.setUserName(studentVo.getName());

        System.out.println(studentDto);
    }
}
