package com.lvshen.demo.annotation.easyexport.utils;

import com.lvshen.demo.annotation.easyexport.DocumentInfo;
import com.lvshen.demo.json.json2list.JsonUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-12-20 14:14
 * @since JDK 1.8
 */
@Component
public class DocumentApiClient {


    public DocumentInfo getDocumentInfoByFileId(String fileId) {
        //todo 文件查询接口。根据实际情况获取
        return new DocumentInfo();
    }
    public ScUploadFileResponse upload(MultipartFile file) {
        //todo 文件查询接口。根据实际情况获取
        return null;
    }

}
