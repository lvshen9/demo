package com.lvshen.demo.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.lvshen.demo.annotation.easyexport.DocumentInfo;
import com.lvshen.demo.annotation.easyexport.ExportToDocumentVo;
import com.lvshen.demo.annotation.easyexport.utils.*;
import com.lvshen.demo.annotation.sensitive.SpringContextHolder;
import com.lvshen.demo.json.json2list.JsonUtils;
import com.lvshen.demo.redis.RedisUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2021/4/17 13:44
 * @since JDK 1.8
 */
@Slf4j
@Component
public class EasyExcelAsyncUtils {

    private static final int PAGE_LIMIT = 10000;
    public static final RedisUtils redisApiClient = SpringContextHolder.getBean(RedisUtils.class);
    private static final String EXPORT_REDIS_KEY = "sc:common:async:export:excel:%s:%s:%s";

    public static ByteArrayOutputStream asyncExportExcel(HttpServletResponse response, List<? extends BaseRowModel> list, Class clazz, String fileName, String sheetName, Executor taskAsyncExecutor, String taskId) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        StopWatch start = TimeWatchUtils.start();
        fileName = StringUtils.isBlank(fileName) ? "excel文件导出" : fileName;
        int size = list.size();
        log.info("准备导出数据：【{}】条", size);
        int sizeLimit = PAGE_LIMIT;
        try {
            buildResponse(response, fileName);
            if (size > sizeLimit) {
                List<? extends List<? extends BaseRowModel>> partitions = Lists.partition(list, sizeLimit);
                int countSize = partitions.size();
                BlockingQueue<List<? extends BaseRowModel>> queue = new ArrayBlockingQueue<>(countSize);
                final CountDownLatch countDownLatch = new CountDownLatch(countSize);
                final CountDownLatch takeQueueCountDownLatch = new CountDownLatch(countSize);
                ServletOutputStream outputStream = response.getOutputStream();
                try {
                    ExcelWriter excelWriter = EasyExcel.write(outputStream, clazz).build();
                    partitions.stream().<Runnable>map(item -> () -> {
                        try {
                            queue.put(item);
                        } catch (Exception e) {
                            log.error("导出文件异常：", e);
                        } finally {
                            countDownLatch.countDown();
                        }
                    }).forEach(taskAsyncExecutor::execute);

                    taskAsyncExecutor.execute(() -> {
                        int sheetNo = 0;

                        while (true) {
                            List<? extends BaseRowModel> consumerList = null;
                            try {
                                consumerList = queue.take();
                            } catch (InterruptedException e) {
                                Thread.interrupted();
                            }

                            long count = countDownLatch.getCount();
                            WriteSheet writeSheet = EasyExcel.writerSheet(sheetNo, sheetName.concat("_").concat(String.valueOf(sheetNo)))
                                    .head(clazz)
                                    .registerWriteHandler(new Custemhandler())
                                    .registerWriteHandler(getStyleStrategy())
                                    .build();

                            excelWriter.write(consumerList, writeSheet);
                            sheetNo++;
                            takeQueueCountDownLatch.countDown();
                            if (count == 0 && queue.size() == 0) {
                                break;
                            }
                            if (countSize / sheetNo == 2) {
                                ExportToDocumentVo vo = getExportToDocumentVoByTaskId(taskId);
                                vo.setProgress("50");
                                createOrUpdateToRedis(vo);
                            }
                        }
                    });
                    try {
                        takeQueueCountDownLatch.await();
                        excelWriter.finish();
                    } catch (InterruptedException e) {
                        log.error("asyncExportExcel线程等待任务时发生应用错误：", e);
                    }
                } catch (Exception e) {
                    log.error("导出异常", e);
                }
            } else {
                // 这里需要设置不关闭流
                EasyExcel.write(response.getOutputStream(), clazz)
                        .autoCloseStream(Boolean.FALSE)
                        .sheet(sheetName)
                        .registerWriteHandler(new Custemhandler())
                        .registerWriteHandler(getStyleStrategy())
                        .doWrite(list);
            }
        } catch (Exception e) {
            // 重置response
            resetResponse(response, e);
        }
        TimeWatchUtils.stop(start, "asyncExportExcel");
        log.info("导出结束!");
        return byteArrayOutputStream;
    }


    public static void resetResponse(HttpServletResponse response, Exception e) {
        response.reset();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        Map<String, String> map = new HashMap<>();
        map.put("status", "failure");
        map.put("message", "下载文件失败" + e.getMessage());
        try {
            response.getWriter().println(JSON.toJSONString(map));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static void buildResponse(HttpServletResponse response, String fileName) throws
            UnsupportedEncodingException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileSuffix = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        fileName = fileSuffix.concat("-").concat(fileName);
        String fileNameAfterEncode = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename=" + fileNameAfterEncode + ".xlsx");
    }

    public static <T> List<T> importExcel(MultipartFile file, Class<T> className) {
        ImportExcelListener<T> importExcelListener = new ImportExcelListener<>();
        try {
            EasyExcel.read(file.getInputStream(), className, importExcelListener).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return importExcelListener.getClassList();
    }


static class ImportExcelListener<T> extends AnalysisEventListener<T> {

    @Getter
    private final List<T> classList = new ArrayList<>();

    public ImportExcelListener() {
        super();
        classList.clear();
    }

    @Override
    public void invoke(T aClass, AnalysisContext analysisContext) {
        classList.add(aClass);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

}

    public static HorizontalCellStyleStrategy getStyleStrategy() {
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景设置为灰色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 12);
        // 字体样式
        headWriteFont.setFontName("微软雅黑");
        headWriteCellStyle.setWriteFont(headWriteFont);
        //自动换行
        headWriteCellStyle.setWrapped(false);
        // 水平对齐方式
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        // 垂直对齐方式
        headWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.头默认了 FillPatternType所以可以不指定
        // contentWriteCellStyle.setFillPatternType(FillPatternType.SQUARES);
        // 背景白色
        contentWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        WriteFont contentWriteFont = new WriteFont();
        // 字体大小
        contentWriteFont.setFontHeightInPoints((short) 10);
        // 字体样式
        contentWriteFont.setFontName("Arial");
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        return new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
    }

    private static ExportToDocumentVo getExportToDocumentVoByTaskId(String taskId) {
        String redisKey = getExportRedisKey();
        String voStr = (String) redisApiClient.hget(redisKey, taskId);
        return JsonUtils.str2obj(voStr, ExportToDocumentVo.class);
    }

    public static String startExportToDocument(String fileName, String taskId, int saveTime) {
        ExportToDocumentVo vo = new ExportToDocumentVo();
        vo.setExportStartDate(new Date());
        if (StringUtils.isBlank(fileName)) {
            fileName = "Excel导出";
        }
        vo.setFilename(fileName.concat(".xlsx"));
        vo.setProgress(SystemConstant.ZERO);
        vo.setTaskId(taskId);
        vo.setSaveTime(saveTime);
        return createOrUpdateToRedis(vo);
    }

    public static String endExportToDocument(DocumentInfo info) {
        ExportToDocumentVo vo = getExportToDocumentVoByTaskId(info.getTaskId());
        vo.setFileId(info.getFileId());
        vo.setProgress("100");
        vo.setExportEndDate(new Date());
        vo.setMimeType(info.getMimeType());
        vo.setPath(info.getPath());
        vo.setSize(String.valueOf(info.getSize()));
        vo.setSaveTime(info.getSaveTime());
        return createOrUpdateToRedis(vo);
    }

    /**
     * 更新到Redis
     *
     * @param vo
     * @return
     */
    private static String createOrUpdateToRedis(ExportToDocumentVo vo) {
        String redisKey = getExportRedisKey();
        if (vo != null) {
            String vo2Json = JsonUtils.toJsonString(vo);
            String taskId = vo.getTaskId();
            Integer saveTime = vo.getSaveTime();
            redisApiClient.hset(redisKey, taskId, vo2Json, saveTime * 60 * 60);
            return "存入Redis成功";
        }
        return "未存入redis，无文件信息";
    }

    /**
     * 获取redis key
     *
     * @return
     */
    public static String getExportRedisKey() {
        //获取当前租户
        String tenantId = "0001";
        if (StringUtils.isBlank(tenantId)) {
            tenantId = "1000000000";
        }
        String module = ScUtils.getCurrentSystem();
        String currentUserAccount = getCurrentUserAccount();
        if (StringUtils.isBlank(currentUserAccount)) {
            currentUserAccount = "admin";
        }
        String key = String.format(EXPORT_REDIS_KEY, tenantId, module, currentUserAccount);
        log.info("使用的key: [{}]", key);
        return key;
    }

    private static String getCurrentUserAccount() {
        AuthUser currentUser = CurrentRuntimeContext.getCurrentUser();
        String currentUserAccount;
        currentUserAccount = Optional.ofNullable(currentUser).orElseGet(AuthUser::new).getUsername();
        if (StringUtils.isBlank(currentUserAccount)) {
            currentUserAccount = "admin";
        }
        return currentUserAccount;
    }


}
