package com.lvshen.demo.annotation.easyexport;

import lombok.Data;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-12-20 8:38
 * @since JDK 1.8
 */
@Data
public class ExcelPageQueryRequest {
    private String fileName;
    private String sheetName;
}
