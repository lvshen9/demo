package com.lvshen.demo.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2021/4/17 11:46
 * @since JDK 1.8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
/**设置 row 高度，不包含表头*/
@ContentRowHeight(25)
/**设置 表头 高度（与 @ContentRowHeight 相反）*/
@HeadRowHeight(25)
/**设置列宽*/
@ColumnWidth(25)
@Accessors(chain = true)
public class TeacherInfoDto extends BaseRowModel {
    /**设置表头信息*/
    @ExcelProperty("id")
    private Integer id;

    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("电话")
    private String phone;

    @ExcelProperty("地址")
    private String address;

    @ExcelProperty("时间")
    private String enrolDate;

    @ExcelProperty("备注")
    private String des;
}
