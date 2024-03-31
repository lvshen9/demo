package com.lvshen.demo.annotation.log2;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2024/3/31 19:08
 * @since JDK 1.8
 */
@Getter
@AllArgsConstructor
public enum OperateTypeEnum implements BaseEnum {
    /**
     * QUERY-查看，INSERT-新增，UPDATE-修改，DELETE-删除，
     * UPLOAD-上传，DOWNLOAD-下载，LOGIN-登录，IMPORT-导入，EXPORT-导出
     */
    QUERY("查看", "QUERY"),
    INSERT("新增", "INSERT"),
    UPDATE("修改", "UPDATE"),
    DELETE("删除", "DELETE"),
    UPLOAD("上传", "UPLOAD"),
    DOWNLOAD("下载", "DOWNLOAD"),
    LOGIN("登录", "LOGIN"),
    IMPORT("导入", "IMPORT"),
    EXPORT("导出", "EXPORT");

    private String desc;
    private String value;
}
