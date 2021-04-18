package com.lvshen.demo.exportTest;

import cn.hutool.core.lang.Console;
import com.lvshen.demo.easyexcel.ExcelService;
import com.lvshen.demo.easyexcel.TeacherInfoDto;
import com.lvshen.demo.member.entity.Member;
import com.lvshen.demo.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-8-31 15:14c
 * @since JDK 1.8
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class Test {
    @Autowired
    private MemberService memberService;
    @Autowired
    private ExcelService excelService;

    @org.junit.Test
    public void test() {
        HttpServletResponse response = new MockHttpServletResponse();
        List<Member> members = memberService.listMemberByAnnotation(response);
        System.out.println(members);
    }

    /**
     * 导出带有表头注解的Java实体类模型
     * 导出单个sheet 的Excel，带表头
     */
    @org.junit.Test
    public void writeExcel() {
        String filePath = "D:/study/code/demo/src/test/java/com/lvshen/demo/exportTest/";
        String fileName = "exportTest.xlsx";
        String sheetName = "TeacherInfo";
        String fullName = filePath.concat(fileName);
        List<TeacherInfoDto> teacherInfoDtos = excelService.listInfo();
        excelService.exportInfo(fullName, sheetName, teacherInfoDtos);
        Console.log("导出结束");
    }
}
