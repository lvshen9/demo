package com.lvshen.demo.annotation.easyexport;

import lombok.Data;

import java.util.Date;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-12-20 14:16
 * @since JDK 1.8
 */
@Data
public class ExportToDocumentVo {
    /**
     * 文件id
     */
    private String fileId;
    /**
     * 导出开始时间
     */
    private Date exportStartDate;
    /**
     * 导出时间
     */
    private Date exportEndDate;
    /**
     * 下载路径
     */
    private String path;
    /**
     * 文件大小
     */
    private String size;
    /**
     * 文件名称
     */
    private String filename;
    /**
     * 文件类型
     */
    private String mimeType;

    /**
     * 下载进度
     */
    private String progress;

    /**
     * 任务id
     */
    private String taskId;

    /**
     * 保存时间
     */
    private Integer saveTime;
}
