package com.lvshen.demo.logexception.apilog;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.lvshen.demo.catchexception.BusinessException;
import com.lvshen.demo.catchexception.BusinessExceptionAssert;
import com.lvshen.demo.common.MyStringUtils;
import com.lvshen.demo.json.json2list.JsonUtils;
import com.lvshen.demo.logexception.dao.ApiLog;
import com.lvshen.demo.logexception.dao.ApiLogService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Description: 日志执行
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022/5/19 9:48
 * @since JDK 1.8
 */
@Slf4j
//@Component
public class LogExecutor {
    @Autowired
    private ApiLogService apiLogService;
    @Autowired
    private RetryResultApplicationService retryResultApplicationService;
    @Autowired
    private ExceptionResultQueueService exceptionResultQueueService;

    private static final String LIST_STR = "java.util.List";
    private static final String NULL_STR = "null";

    public Object executorMethod(String classStr, String methodStr, String requestStr, String requestTypeStr) throws Exception {
        Class clz = Class.forName(classStr);
        Object classBean = clz.newInstance();
        //获取方法
        String paramType = null;
        if (requestTypeStr.contains(LIST_STR)) {
            paramType = requestTypeStr.substring(requestTypeStr.indexOf("<"), requestTypeStr.indexOf(">")).substring(1);
            requestTypeStr = LIST_STR;
        }

        Class requestTypeClz = Class.forName(requestTypeStr);
        Method m = classBean.getClass().getDeclaredMethod(methodStr, requestTypeClz);
        Object requestObj = JsonUtils.str2obj(requestStr, requestTypeClz);
        if (requestTypeStr.contains(LIST_STR)) {
            String obj2string = JSONUtil.toJsonStr(requestObj);
            JSONArray jsonArray = JSONUtil.parseArray(obj2string);
            requestObj = JSONUtil.toList(jsonArray, Class.forName(paramType));
        }
        return m.invoke(classBean, requestObj);
    }

    @Data
    public static class LogMethodParam {
        /**
         * 异常日志id
         */
        private String logId;
        /**
         * 接口返回成功标识,可不填
         */
        private String resultSuccess;
    }
    public Object executorMethod(LogMethodParam param) {
        String logId = param.getLogId();
        String resultSuccessParam = param.getResultSuccess();
        ApiLog apiLog = apiLogService.getApiLogById(logId);

        if (apiLog == null) {
            return String.format("参数日志信息不可查，日志id【%s】", logId);
        }

        String isException = apiLog.getIsException();
        String successStrFromDb = apiLog.getSuccessStr();
        BusinessExceptionAssert.checkArgument("1".equals(isException), String.format("此条日志【%s】为请求正常日志，无需重试", logId));
        String url = apiLog.getUrl();
        List<String> urlStr2List = MyStringUtils.string2List(url, ".");
        StringBuilder classSb = new StringBuilder();
        int length = urlStr2List.size() - 1;
        for (int i = 0; i < length; i++) {
            classSb.append(urlStr2List.get(i));
            if (i != length - 1) {
                classSb.append(".");
            }
        }
        String classStr = classSb.toString();
        String methodStr = urlStr2List.get(length);
        String requestStr = apiLog.getRequest();
        String requestTypeStr = apiLog.getParamTypeName();
        long retry = apiLog.getRetry() == null ? 0L : apiLog.getRetry();
        Long allowRetry = apiLog.getAllowRetry() == null ? -1 : apiLog.getAllowRetry();
        boolean canRetry = allowRetry == -1 || (retry + 1 <= allowRetry);
        if (!canRetry) {
            return String.format("超过允许的最大重试次数【%s】了,当前重试次数【%s】", allowRetry, retry + 1);
        }
        String invokeMethod = apiLog.getInvokeMethod();
        String executeClassStr = StringUtils.isBlank(invokeMethod) ? null : invokeMethod.concat("#").concat(methodStr);

        Object result;

        try {
            //重试执行异常的方法
            result = executorMethod(classStr, methodStr, requestStr, requestTypeStr);
            //方法执行后需要修改业务数据信息
            retryResultApplicationService.resultHandler(executeClassStr, result);
            updateApiLogAfterExecutor(logId, resultSuccessParam, successStrFromDb, retry, result);
        } catch (Exception e) {
            log.error("执行方法异常", e);
            String errorException = String.format("方法执行异常，异常信息【%s】，日志id【%s】", e, logId);
            throw new BusinessException(errorException);
        }
        return String.format("方法执行结束，日志id【%s】，执行结果【%s】", logId, result);
    }

    private void updateApiLogAfterExecutor(String logId, String resultSuccessParam, String successStrFromDb, long retry, Object o) {
        String resultStr = JsonUtils.toJsonString(o);
        boolean needUpdateNormal = needUpdateNormal(resultSuccessParam, successStrFromDb, resultStr);
        ApiLog update = new ApiLog();
        update.setId(logId);
        update.setResult(resultStr);
        update.setRetry(retry + 1);
        if (needUpdateNormal) {
            update.setIsException("0");
        } else {
            update.setResult(resultStr);
            //放入异常队列
            exceptionResultQueueService.pushExceptionQueue(logId);
        }
        apiLogService.updateApiLog(update);
    }

    private boolean needUpdateNormal(String resultSuccessParam, String successStrFromDb, String resultStr) {
        if (StringUtils.isBlank(resultStr) || NULL_STR.equals(resultStr)) {
            return true;
        }
        if (StringUtils.isBlank(resultSuccessParam)) {
            return StringUtils.isBlank(successStrFromDb) || resultStr.contains(successStrFromDb);
        }
        return resultStr.contains(resultSuccessParam);
    }
}


