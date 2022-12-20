package com.lvshen.demo.annotation.easyexport;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.alibaba.excel.metadata.BaseRowModel;
import com.lvshen.demo.annotation.sensitive.SpringContextHolder;
import com.lvshen.demo.catchexception.BusinessExceptionAssert;
import com.lvshen.demo.json.json2list.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-12-20 13:55
 * @since JDK 1.8
 */
@Component
@Aspect
@Slf4j
public class EasyExcelAspect {
    private static final Snowflake SNOW_FLAKE = IdUtil.getSnowflake(1, 1);

    @Pointcut("@annotation(com.lvshen.demo.annotation.easyexport.EasyExcelExport)")
    public void exportPointcut() {
    }

    @Around("exportPointcut()")
    public void doExport(ProceedingJoinPoint point) throws Throwable {

        Object o = point.proceed();
        if (o instanceof List) {
            List list = (List) o;

            MethodSignature signature = (MethodSignature) point.getSignature();
            Method method;
            method = point.getTarget().getClass().getMethod(signature.getName(), signature.getMethod().getParameterTypes());
            EasyExcelExport annotation = method.getAnnotation(EasyExcelExport.class);
            Object[] args = point.getArgs();
            //2.获取方法第一个参数值
            HttpServletResponse response = (HttpServletResponse) args[0];
            Object arg1 = args[1];
            String fileName = null;
            String sheetName = null;
            if (arg1 instanceof ExcelPageQueryRequest) {
                ExcelPageQueryRequest queryRequest = (ExcelPageQueryRequest) arg1;
                fileName = queryRequest.getFileName();
                sheetName = queryRequest.getSheetName();
            }
            if (CollectionUtils.isEmpty(list)) {
                log.info("无导出数据");
                return;
            }
            Object resultOne = list.get(0);
            BusinessExceptionAssert.checkArgument(resultOne instanceof BaseRowModel, "返回实体对象需要继承BaseRowModel类");
            int asyncSize = annotation.asyncSize();
            int maxSize = annotation.maxSize();
            int saveIime = annotation.saveTime();
            int size = list.size();
            BusinessExceptionAssert.checkArgument(size <= maxSize, "导出数据条数%s大于最大限制条数%s", size, maxSize);
            if (size > asyncSize) {
                //异步导出接口...
                String excelPoolBean = annotation.excelPoolBeanName();
                Executor excelThreadPool = null;
                if (StringUtils.isNotBlank(excelPoolBean)) {
                    excelThreadPool = (Executor) SpringContextHolder.getBean(excelPoolBean);
                }
                //生成任务id
                String taskId = SNOW_FLAKE.nextIdStr();
                EasyExcelApi.startExportToDocument(fileName, taskId, saveIime);
                DocumentInfo info = EasyExcelApi.asyncExportExcel2Document(response, list, resultOne.getClass(), fileName, sheetName, excelThreadPool, taskId);
                info.setTaskId(taskId);
                info.setSaveTime(saveIime);
                EasyExcelApi.endExportToDocument(info);
                log.info("异步导出，上传到文件系统，文件信息：【{}】", JsonUtils.toJsonString(info));
                return;
            }
            EasyExcelApi.exportExcel(response, list, resultOne.getClass(), fileName, sheetName);
        }

    }
}
