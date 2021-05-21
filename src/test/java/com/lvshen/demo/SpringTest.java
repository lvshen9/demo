package com.lvshen.demo;

import com.alibaba.fastjson.JSON;
import com.lvshen.demo.annotation.sensitive.UserInfo;
import com.lvshen.demo.annotation.validator.GeneratorStudentService;
import com.lvshen.demo.treenode.Student;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2021/4/10 11:34
 * @since JDK 1.8
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringTest {

    @Autowired
    private GeneratorStudentService generatorStudentService;

    @org.junit.Test
    public void test() {
        UserInfo userInfo = new UserInfo();
        userInfo.setAge(23);
        userInfo.setIdCard("32133458888988771");
        userInfo.setMobile("13456782345");
        userInfo.setNativePlace("北京海定区");
        userInfo.setSex("男");
        userInfo.setUseId(1234567L);
        userInfo.setUseName("爱德华");
        userInfo.setUseNo("11");

        String json= JSON.toJSONString(userInfo);
        System.out.println(json);

    }

    @Test
    public void testCreateStudent() {
        Student student = generatorStudentService.initData();
        generatorStudentService.creatStudent(student);
        System.out.println("测试结束...");
    }
}
