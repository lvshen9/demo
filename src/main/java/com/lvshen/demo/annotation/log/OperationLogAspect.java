package com.lvshen.demo.annotation.log;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/10/8 10:57
 * @since JDK 1.8
 */
@Aspect
@Component
public class OperationLogAspect {

    //@Value("${version}")
    private String operVer;

    @Autowired
    private OperationLogService operationLogService;

    @Autowired
    private ExceptionLogService exceptionLogService;

    private static Snowflake snowflake = IdUtil.createSnowflake(1, 1);


    /**
     * 设置操作日志切入点 记录操作日志 在注解的位置切入代码
     */
    @Pointcut("@annotation(com.lvshen.demo.annotation.log.OperationLog)")
    public void logPointCut() {
    }

    /**
     * 设置操作异常切入点记录异常日志 扫描所有controller包下操作
     */
    @Pointcut("execution(* com.lvshen.demo.annotation.log.controller..*.*(..))")
    public void exceptionLogPointCut() {
    }

    /**
     * 正常返回通知，拦截用户操作日志，连接点正常执行完成后执行， 如果连接点抛出异常，则不会执行
     *
     * @param joinPoint 切入点
     * @param keys      返回结果
     */
    @AfterReturning(value = "logPointCut()", returning = "keys")
    public void saveOperationLog(JoinPoint joinPoint, Object keys) {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes
                .resolveReference(RequestAttributes.REFERENCE_REQUEST);

        OperationLogRecord logRecord = new OperationLogRecord();
        try {
            logRecord.setId(String.valueOf(snowflake.nextId())); // 主键ID

            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
            Method method = signature.getMethod();
            // 获取操作
            OperationLog opLog = method.getAnnotation(OperationLog.class);
            if (opLog != null) {
                String module = opLog.module();
                String type = opLog.type();
                String desc = opLog.desc();
                logRecord.setModule(module); // 操作模块
                logRecord.setType(type); // 操作类型
                logRecord.setDesc(desc); // 操作描述
            }
            // 获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            // 获取请求的方法名
            String methodName = method.getName();
            methodName = className + "." + methodName;

            logRecord.setMethod(methodName); // 请求方法

            // 请求的参数
            Map<String, String> rtnMap = convertMap(request.getParameterMap());
            // 将参数所在的数组转换成json
            String params = JSON.toJSONString(rtnMap);

            logRecord.setReqParam(params); // 请求参数
            logRecord.setRespParam(JSON.toJSONString(keys)); // 返回结果
            //logRecord.setUserId(UserShiroUtil.getCurrentUserLoginName()); // 请求用户ID
            //logRecord.setUserName(UserShiroUtil.getCurrentUserName()); // 请求用户名称
            logRecord.setIp(request.getRemoteAddr()); // 请求IP
            logRecord.setUri(request.getRequestURI()); // 请求URI
            logRecord.setCreatTime(DateUtil.date()); // 创建时间
            logRecord.setVer(operVer); // 操作版本
            operationLogService.insert(logRecord);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 异常返回通知，用于拦截异常日志信息 连接点抛出异常后执行
     *
     * @param joinPoint 切入点
     * @param e         异常信息
     */
    @AfterThrowing(pointcut = "exceptionLogPointCut()", throwing = "e")
    public void saveExceptionLog(JoinPoint joinPoint, Throwable e) {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes
                .resolveReference(RequestAttributes.REFERENCE_REQUEST);

        ExceptionLog exceptionLog = new ExceptionLog();
        try {
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
            Method method = signature.getMethod();
            exceptionLog.setId(String.valueOf(snowflake.nextId()));
            // 获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            // 获取请求的方法名
            String methodName = method.getName();
            methodName = className + "." + methodName;
            // 请求的参数
            Map<String, String> rtnMap = convertMap(request.getParameterMap());
            // 将参数所在的数组转换成json
            String params = JSON.toJSONString(rtnMap);
            exceptionLog.setReqParam(params); // 请求参数
            exceptionLog.setMethod(methodName); // 请求方法名
            exceptionLog.setExceptionName(e.getClass().getName()); // 异常名称
            exceptionLog.setExceptionMessage(stackTraceToString(e.getClass().getName(), e.getMessage(), e.getStackTrace())); // 异常信息
            //exceptionLog.setUserId(UserShiroUtil.getCurrentUserLoginName()); // 操作员ID
            //exceptionLog.setUserName(UserShiroUtil.getCurrentUserName()); // 操作员名称
            exceptionLog.setUri(request.getRequestURI()); // 操作URI
            exceptionLog.setIp(request.getRemoteAddr()); // 操作员IP
            exceptionLog.setVer(operVer); // 操作版本号
            exceptionLog.setCreatTime(DateUtil.date()); // 发生异常时间

            exceptionLogService.insert(exceptionLog);

        } catch (Exception e2) {
            e2.printStackTrace();
        }

    }

    /**
     * 转换request 请求参数
     *
     * @param paramMap request获取的参数数组
     */
    public Map<String, String> convertMap(Map<String, String[]> paramMap) {
        Map<String, String> rtnMap = new HashMap<>();
        for (String key : paramMap.keySet()) {
            rtnMap.put(key, paramMap.get(key)[0]);
        }
        return rtnMap;
    }

    /**
     * 转换异常信息为字符串
     *
     * @param exceptionName    异常名称
     * @param exceptionMessage 异常信息
     * @param elements         堆栈信息
     */
    public String stackTraceToString(String exceptionName, String exceptionMessage, StackTraceElement[] elements) {
        StringBuffer stringBuffer = new StringBuffer();
        for (StackTraceElement stet : elements) {
            stringBuffer.append(stet + "\n");
        }
        String message = exceptionName + ":" + exceptionMessage + "\n\t" + stringBuffer.toString();
        return message;
    }
}

