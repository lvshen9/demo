package com.lvshen.demo.logexception.apilog;

import com.lvshen.demo.json.json2list.JsonUtils;
import com.lvshen.demo.logexception.dao.ApiLog;
import com.lvshen.demo.logexception.dao.ApiLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * @ClassName: AccessAspect
 * @Description: 日志切面处理
 * @Author: lvshen
 * @CreateDate: 2021/12/14 16:49
 * @UpdateUser: lvshen
 * @UpdateDate: 2021/12/14 16:49
 * @Version: v1.0
 */
@Slf4j
@Aspect
@Component
public class ApiLogAspect {

    @Autowired
    private ApiLogService apiLogService;

    @Value("${spring.application.name}")
    private String applicationName;

    /**
     * 切点.
     */
    @Pointcut("@annotation(com.lvshen.demo.logexception.apilog.EnableLog)")
    public void authPoint() {
    }


    /**
     * 日志切面处理.
     *
     * @param joinPoint point
     * @return 返回
     * @throws Throwable 异常
     */
    @Around("authPoint()")
    public Object flowAspect(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        EnableLog enableLog = method.getAnnotation(EnableLog.class);
        OperationType type = enableLog.type();
        String provider = enableLog.provider();
        ModelType model = enableLog.model();
        String currentSystem = enableLog.currentSystem();

        Object[] args = joinPoint.getArgs();
        Type[] genericParameterTypes = method.getGenericParameterTypes();
        String typeName = genericParameterTypes.length == 0 ? StringUtils.EMPTY : genericParameterTypes[0].getTypeName();
        Object result = null;
        ApiLog apiLog = new ApiLog();
        apiLog.setModel(model.name());
        apiLog.setParamTypeName(typeName);
        String ipAddress = null;
        if (model.name().equals(ModelType.OUT.name())) {
            ipAddress = getLocalIpForLinux();
        }
        if (model.name().equals(ModelType.IN.name())) {
            ipAddress = getRequestIp();
        }
        if (StringUtils.isBlank(currentSystem)) {
            currentSystem = getCurrentSystem();
        }
        String successStr = enableLog.successStr();

        //获取类的字节码对象，通过字节码对象获取方法信息
        Class<?> targetCls = joinPoint.getTarget().getClass();
        //获取方法签名(通过此签名获取目标方法信息)
        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        //获取目标方法名(目标类型+方法名)
        String targetClsName = targetCls.getName();
        String targetObjectMethodName = targetClsName + "." + ms.getName();

        apiLog.setCurrentSystem(currentSystem);
        apiLog.setReqType(type.name());
        apiLog.setProvider(provider);
        apiLog.setUrl(targetObjectMethodName);
        String request = args.length == 0 ? StringUtils.EMPTY : JsonUtils.toJsonString(args[0]);
        apiLog.setRequest(request);
        apiLog.setSuccessStr(successStr);
        apiLog.setIpAddr(ipAddress);
        apiLog.unDeleted();
        try {
            result = joinPoint.proceed();
            String resultStr = JsonUtils.toJsonString(result);
            apiLog.setResult(resultStr);
            if (StringUtils.isBlank(successStr) || resultStr.contains(successStr)) {
                apiLog.setIsException("0");
            } else {
                apiLog.setIsException("1");
            }
        } catch (Exception e) {
            String resultException = (e instanceof NullPointerException) ? "[NullPointerException]" + JsonUtils.toJsonString(e.getCause()) : e.getMessage();
            apiLog.setResult(resultException);
            apiLog.setIsException("1");
        }
        try {
            apiLogService.addApiLog(apiLog);
        } catch (Exception e) {
            log.error("日志插入异常", e);
        }
        return result;
    }

    public String getCurrentSystem() {
        String unKnown = "未知系统";
        try {
            String property = applicationName;
            return StringUtils.isEmpty(property) ? unKnown : property;
        } catch (Exception e) {
            return unKnown;
        }
    }

    /**
     * 当前系统ip
     *
     * @return
     */
    private String getLocalIpForLinux() {
        String hostIP = "";
        try {
            Enumeration networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) networkInterfaces.nextElement();
                Enumeration nias = ni.getInetAddresses();
                while (nias.hasMoreElements()) {
                    InetAddress ia = (InetAddress) nias.nextElement();
                    if (!ia.isLinkLocalAddress() && !ia.isLoopbackAddress() && ia instanceof Inet4Address) {
                        hostIP = ia.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return hostIP;
    }

    /**
     * 请求ip
     *
     * @return
     */
    private String getRequestIp() {
        HttpServletRequest request = null;
        try {
            RequestAttributes ra = RequestContextHolder.getRequestAttributes();
            ServletRequestAttributes sra = (ServletRequestAttributes) ra;
            request = sra.getRequest();
        } catch (Exception e) {
        }
        return request.getRemoteAddr();
    }
}

