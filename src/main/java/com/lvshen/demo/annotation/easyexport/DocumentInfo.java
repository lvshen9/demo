package com.lvshen.demo.annotation.easyexport;

import lombok.Data;

import java.util.Date;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-12-20 14:11
 * @since JDK 1.8
 */
@Data
public class DocumentInfo {
    private String fileId;
    private String metadatas;
    private Date expire;
    private String bizSubType;
    private Date updatedAt;
    private String createdBy;
    private String updatedBy;
    private String updatedUserName;
    private String appId;
    private String path;
    private Long size;
    private String filename;
    private String mimeType;
    private String folder;
    private String sign;
    private Integer timestamp;
    private String bizKey;
    private String taskId;
    private Integer saveTime;
}
