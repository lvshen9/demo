package com.lvshen.demo.annotation.easyexport.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-12-20 14:48
 * @since JDK 1.8
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScUploadFileResponse {
    private String downloadUrl;
    private String originName;
    private String fileKey;
    private String mimeType;
    private Boolean isPrivate;
    /**
     * 文件大小
     */
    private Long size;

    private String fileId;
    /**
     * 上传时间戳
     */
    private Integer timestamp;
    /**
     * 返回标志
     */
    private String sign;
}
