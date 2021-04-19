package com.lvshen.demo.easyexcel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2021/4/17 13:09
 * @since JDK 1.8
 */
@RestController
@RequestMapping(value = "/excel", method = {RequestMethod.GET, RequestMethod.POST})
public class EasyExportController {

    @Autowired
    private ExcelService excelService;

    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public void export(HttpServletResponse response) {
        List<TeacherInfoDto> teacherInfoDtoList = excelService.listInfo();
        excelService.exportExcelWeb(response, teacherInfoDtoList);
    }
}
