package com.lvshen.demo.exportTest;

import cn.hutool.core.lang.Console;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.enums.WriteDirectionEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.fill.FillWrapper;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class ExportExcelTest {
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

    @org.junit.Test
    public void testFillExcel() {
        List<TeacherInfoDto> teacherInfoDtos = excelService.listInfo();
        String filePath = "D:/study/code/demo/src/test/java/com/lvshen/demo/exportTest/";
        String templateFileName = filePath + "FillExcelTemplate.xlsx";

        String fileName = filePath + "compositeFill" + System.currentTimeMillis() + ".xlsx";
        ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(templateFileName).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        FillConfig fillConfig = FillConfig.builder().direction(WriteDirectionEnum.HORIZONTAL).build();
        excelWriter.fill(new FillWrapper("data1", teacherInfoDtos), fillConfig, writeSheet);
        excelWriter.fill(new FillWrapper("data2", teacherInfoDtos), writeSheet);

        Map<String, Object> map = new HashMap<>();
        map.put("date", "2021年4月18日19:34:28");
        excelWriter.fill(map, writeSheet);

        // 别忘记关闭流
        excelWriter.finish();
    }
}
