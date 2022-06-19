package com.lvshen.demo.logexception.apilog;

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
@Component
public class LogExecutor {
    @Autowired
    private ApiLogService apiLogService;

    public Object executorMethod(String classStr, String methodStr, String requestStr, String requestTypeStr) throws Exception {
        Class clz = Class.forName(classStr);
        Object classBean = clz.newInstance();
        //获取方法
        Class requestTypeClz = Class.forName(requestTypeStr);
        Method m = classBean.getClass().getDeclaredMethod(methodStr, requestTypeClz);
        Object requestObj = JsonUtils.str2obj(requestStr, requestTypeClz);
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

        try {
            Object o = executorMethod(classStr, methodStr, requestStr, requestTypeStr);
            String resultStr = JsonUtils.toJsonString(o);
            boolean needUpdate = needUpdate(resultSuccessParam, successStrFromDb, resultStr);
            if (needUpdate) {
                apiLogService.updateNormalStatus(logId);
            }
        } catch (Exception e) {
            log.error("执行方法异常", e);
            return String.format("方法执行异常，异常信息【%s】，日志id【%s】", e, logId);
        }
        return String.format("方法执行结束，日志id【%s】", logId);
    }

    private boolean needUpdate(String resultSuccessParam, String successStrFromDb, String resultStr) {
        if (StringUtils.isBlank(resultStr)) {
            return true;
        }
        if (StringUtils.isBlank(resultSuccessParam)) {
            if (StringUtils.isBlank(successStrFromDb) || resultStr.contains(successStrFromDb)) {
                return true;
            }
        }
        return resultStr.contains(resultSuccessParam);
    }
}


