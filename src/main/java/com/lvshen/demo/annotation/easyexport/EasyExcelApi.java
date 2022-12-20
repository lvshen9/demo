package com.lvshen.demo.annotation.easyexport;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.metadata.BaseRowModel;
import com.google.common.collect.ImmutableList;
import com.lvshen.demo.annotation.easyexport.utils.*;
import com.lvshen.demo.annotation.sensitive.SpringContextHolder;
import com.lvshen.demo.catchexception.BusinessException;
import com.lvshen.demo.easyexcel.EasyExcelAsyncUtils;
import com.lvshen.demo.json.json2list.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

import static com.lvshen.demo.easyexcel.EasyExcelAsyncUtils.*;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-12-20 14:12
 * @since JDK 1.8
 */
@Slf4j
public class EasyExcelApi {
    private static final DocumentApiClient documentApiClient = SpringContextHolder.getBean(DocumentApiClient.class);
    private static final Executor excelThreadPool = (Executor) SpringContextHolder.getBean("excelThreadPool");

    public static DocumentInfo asyncExportExcel2Document(HttpServletResponse response, List<? extends BaseRowModel> list, Class clazz, String fileName, String sheetName, Executor taskAsyncExecutor, String taskId) {
        try {
            if (taskAsyncExecutor == null) {
                taskAsyncExecutor = excelThreadPool;
            }
            if (StringUtils.isBlank(sheetName)) {
                sheetName = "sheet1";
            }
            if (StringUtils.isBlank(fileName)) {
                fileName = "Excel导出";
            }
            ByteArrayOutputStream arrayOutputStream = EasyExcelAsyncUtils.asyncExportExcel(response, list, clazz, fileName, sheetName, taskAsyncExecutor, taskId);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(arrayOutputStream.toByteArray());
            MultipartFile mFile = new MockMultipartFile(fileName + ".xlsx", fileName + ".xlsx", ConstantsString.APPLICATION_OCTET_STREAM, byteArrayInputStream);
            ScUploadFileResponse upload = documentApiClient.upload(mFile);
            String fileId = upload.getFileId();
            return documentApiClient.getDocumentInfoByFileId(fileId);
        } catch (IOException e) {
            throw new BusinessException("创建或者保存excel文件出错");
        }
    }

    public static void startExportToDocument(String fileName, String taskId, int saveIime) {
        EasyExcelAsyncUtils.startExportToDocument(fileName, taskId, saveIime);
    }

    public static void endExportToDocument(DocumentInfo info) {
        EasyExcelAsyncUtils.endExportToDocument(info);
    }

    public static void exportExcel(HttpServletResponse response, List<? extends BaseRowModel> list, Class
            clazz, String fileName, String sheetName) {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        if (StringUtils.isBlank(sheetName)) {
            sheetName = "sheet1";
        }
        if (StringUtils.isBlank(fileName)) {
            fileName = "Excel导出";
        }
        try {
            buildResponse(response, fileName);
            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream(), clazz)
                    .autoCloseStream(Boolean.FALSE)
                    .sheet(sheetName)
                    .registerWriteHandler(new Custemhandler())
                    .registerWriteHandler(getStyleStrategy())
                    .doWrite(list);
        } catch (Exception e) {
            // 重置response
            resetResponse(response, e);
        }
    }

    /**
     * 查询导出的excel文件信息
     * @param pageNo
     * @param pageSize
     * @return
     */
    public static Page<ExportToDocumentVo> pageCurrentExportInfo(Integer pageNo, Integer pageSize) {
        List<ExportToDocumentVo> exportToDocumentVos = listCurrentUserExportDocument(null);
        if (CollectionUtils.isEmpty(exportToDocumentVos)) {
            return new Page<>();
        }
        return buildPage(pageNo, pageSize, exportToDocumentVos);
    }

    private static Page<ExportToDocumentVo> buildPage(Integer pageNo, Integer pageSize, List<ExportToDocumentVo> exportToDocumentVos) {
        if (pageNo == null) {
            pageNo = 0;
        }
        if (pageSize == null) {
            pageSize = SystemConstant.PAGE_LIMIT;
        }
        List<ExportToDocumentVo> listForSkip = exportToDocumentVos.stream().skip((long) (pageNo - 1) * pageSize).limit(pageSize).
                collect(Collectors.toList());
        Page<ExportToDocumentVo> voPage = new Page<>();

        voPage.setData(listForSkip);
        int size = exportToDocumentVos.size();
        voPage.setTotal(size);
        voPage.setPageNo(pageNo);
        voPage.setPageSize(pageSize);
        int pages = (int) Math.ceil((double) size / pageSize);
        voPage.setPages(pages);
        return voPage;
    }

    private static List<ExportToDocumentVo> listCurrentUserExportDocument(String redisKey) {
        if (StringUtils.isBlank(redisKey)) {
            redisKey = getExportRedisKey();
        }
        Map<Object, Object> hmget = redisApiClient.hmget(redisKey);
        if (MapUtils.isEmpty(hmget)) {
            return ImmutableList.of();
        }
        Map<String, ExportToDocumentVo> maps = hmget.entrySet().stream().map(entry ->
                new AbstractMap.SimpleEntry<>((String) entry.getKey(),
                        JsonUtils.str2obj((String) entry.getValue(), ExportToDocumentVo.class))
        ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return new ArrayList<>(maps.values());
    }
}
