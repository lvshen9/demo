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
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

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
//@Component
public class ApiLogAspect {

    @Autowired
    private ApiLogService apiLogService;

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    private ExceptionResultQueueService exceptionResultQueueService;

    private static final String EXECUTOR_METHOD = "executorMethod";

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
        EnableLog annotation = method.getAnnotation(EnableLog.class);
        OperationType type = annotation.type();
        String provider = annotation.provider();
        ModelType model = annotation.model();
        long allowRetry = annotation.allowRetry();
        String needAuto = annotation.needAuto();
        String desc = annotation.desc();
        String currentSystem = annotation.currentSystem();

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
        String successStr = annotation.successStr();

        //获取类的字节码对象，通过字节码对象获取方法信息
        Class<?> targetCls = joinPoint.getTarget().getClass();
        //获取方法签名(通过此签名获取目标方法信息)
        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        //获取目标方法名(目标类型+方法名)
        String targetClsName = targetCls.getName();
        String targetObjectMethodName = targetClsName + "." + ms.getName();
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        List<StackTraceElement> stackTraceElements = Arrays.asList(stackTrace);
        List<String> methodList = stackTraceElements.stream().map(StackTraceElement::getMethodName).collect(Collectors.toList());
        StackTraceElement stackTraceElement = stackTraceElements.get(16);
        String invokeMethodStr = stackTraceElement.getClassName().concat(".").concat(stackTraceElement.getMethodName());

        apiLog.setCurrentSystem(currentSystem);
        apiLog.setReqType(type.name());
        apiLog.setProvider(provider);
        apiLog.setUrl(targetObjectMethodName);
        String request = args.length == 0 ? StringUtils.EMPTY : JsonUtils.toJsonString(args[0]);
        apiLog.setRequest(request);
        apiLog.setSuccessStr(successStr);
        apiLog.setMethodDesc(desc);
        apiLog.setIpAddr(ipAddress);
        apiLog.setAllowRetry(allowRetry);
        apiLog.setNeedAuto(needAuto);
        apiLog.setInvokeMethod(invokeMethodStr);
        apiLog.unDeleted();
        boolean isNeedAutoRetry = "1".equals(needAuto);
        try {
            result = joinPoint.proceed();
            String resultStr = JsonUtils.toJsonString(result);
            apiLog.setResult(resultStr);
            if (StringUtils.isBlank(successStr) || resultStr.contains(successStr)) {
                apiLog.notException();
            } else {
                apiLog.exception();
            }
        } catch (Exception e) {
            String resultException = (e instanceof NullPointerException) ? "[NullPointerException]" + JsonUtils.toJsonString(e.getCause()) : e.getMessage();
            apiLog.setResult(resultException);
            apiLog.exception();
        }
        if (!methodList.contains(EXECUTOR_METHOD)) {
            try {
                apiLogService.addApiLog(apiLog);
            } catch (Exception e) {
                log.error("日志插入异常", e);
            }
            //异常方法日志id存入 redis队列
            String apiLogId = apiLog.getId();
            if (StringUtils.isNotBlank(apiLogId) && "1".equals(apiLog.getIsException()) && isNeedAutoRetry) {
                exceptionResultQueueService.pushExceptionQueue(apiLogId);
            }
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

