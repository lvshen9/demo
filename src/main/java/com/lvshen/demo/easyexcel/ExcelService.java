package com.lvshen.demo.easyexcel;

import com.alibaba.excel.metadata.BaseRowModel;
import com.google.common.collect.Lists;
import com.lvshen.demo.catchexception.BusinessException;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2021/4/17 11:51
 * @since JDK 1.8
 */
@Component
public class ExcelService {

    public List<TeacherInfoDto> listInfo() {
        List<TeacherInfoDto> list = Lists.newArrayList();
        TeacherInfoDto dto = new TeacherInfoDto();
        dto.setId(1);
        dto.setPhone("13346785433");
        dto.setName("Zhouzhou");
        dto.setAddress("中国 珠穆朗玛峰");
        dto.setEnrolDate("2021-04-12");
        dto.setDes("我饿了");
        list.add(dto);

        TeacherInfoDto dto2 = new TeacherInfoDto();
        dto2.setId(2);
        dto2.setPhone("16732678543");
        dto2.setName("Lvshen");
        dto2.setAddress("中国 鄱阳湖");
        dto2.setEnrolDate("2021-04-18");
        dto2.setDes("I can flt");
        list.add(dto2);

        return list;
    }


    public boolean exportInfo(String fileName, String sheetName,List<? extends BaseRowModel> data) {
        FileOutputStream fileOutputStream = null;
        try {
            //获取文件输出流
            fileOutputStream = new FileOutputStream(fileName);
            EasyExcelUtils.writeExcel(fileOutputStream, TeacherInfoDto.class, sheetName, data);
        } catch (Exception e) {
            throw new BusinessException("Excel导出失败!!!");
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
}
