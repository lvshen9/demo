package com.lvshen.demo.annotation.log2;

import com.lvshen.demo.authenticatedstreams.service.ModuleTypeEnum;
import com.lvshen.demo.json.json2list.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2024/3/31 19:17
 * @since JDK 1.8
 */
@Slf4j
@Aspect
@Component
public class LogAspect {
    @Autowired
    private OperateLogService operateLogService;

    @Autowired
    private LogStrategyService logStrategyService;

    private static final String EXECUTOR_METHOD = "executorMethod";

    private static final SpelExpressionParser PARSER = new SpelExpressionParser();

    private static final MyParserContext MY_PARSER_CONTEXT = new MyParserContext();

    /**
     * 切点.
     */
    @Pointcut("@annotation(com.lvshen.demo.annotation.log2.LogRecord)")
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
        LogRecord annotation = method.getAnnotation(LogRecord.class);
        OperateTypeEnum type = annotation.type();
        String provider = annotation.provider();
        ModuleTypeEnum model = annotation.module();
        //
        String busCodeEl = annotation.busCode();
        String remark = annotation.remark();
        String operateLogEl = annotation.operateLog();
        //操作前入参，更新操作时可用
        String paramBeforeEl = annotation.paramBefore();

        Object[] args = joinPoint.getArgs();
        Type[] genericParameterTypes = method.getGenericParameterTypes();
        String typeName = genericParameterTypes.length == 0 ? StringUtils.EMPTY : genericParameterTypes[0].getTypeName();
        Object result = null;
        OperateLogAddParam apiLog = new OperateLogAddParam();
        apiLog.setOperateTypeEnum(type);
        apiLog.setParamTypeName(typeName);

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
        StackTraceElement stackTraceElement = stackTraceElements.get(38);
        String invokeMethodStr = stackTraceElement.getClassName().concat(".").concat(stackTraceElement.getMethodName());

        //对busCode，operateLog，paramBefore进行表达式解析
        StandardEvaluationContext context = getContext(method, args);
        //解析SpringEL表达式
        String busCode = parseSpringEl(context, busCodeEl);

        apiLog.setProvider(provider);
        apiLog.setBusCode(busCode);
        apiLog.setUrl(targetObjectMethodName);
        String request = args.length == 0 ? StringUtils.EMPTY : JsonUtils.toJsonString(args[0]);
        apiLog.setRequest(request);
        apiLog.setModuleTypeEnum(model);
        apiLog.setRemark(remark);

        apiLog.setInvokeMethod(invokeMethodStr);

        //获取当前用户账号
        String currentAccount = "";
        apiLog.setOperateAccount(currentAccount);
        //获取账号名称
        String nameByAccount = "";
        apiLog.setOperateName(nameByAccount);
        String resultStr = null;

        try {
            //执行被注解修饰的方法
            result = joinPoint.proceed();
            resultStr = JsonUtils.toJsonString(result);
            apiLog.setResult(resultStr);
        } catch (Exception e) {
            String resultException = (e instanceof NullPointerException) ? "[NullPointerException]" + JsonUtils.toJsonString(e.getCause()) : e.getMessage();
            apiLog.setResult(resultException);
        }
        //解析paramBeforeEl 在LogRecordContext中的变量
        try {
            Map<String, String> variables = LogRecordContext.getVariables();
            variables.forEach(context::setVariable);
            String paramBefore = parseSpringElWithCustom(paramBeforeEl, context);
            apiLog.setParamBefore(paramBefore);
        } catch (Exception e) {
            String resultException = (e instanceof NullPointerException) ? "[NullPointerException]" + JsonUtils.toJsonString(e.getCause()) : e.getMessage();
            apiLog.setResult(resultException);
        }
        //解析paramBeforeEl 在LogRecordContext中的变量
        try {
            Map<String, String> variables = LogRecordContext.getVariables();
            variables.forEach(context::setVariable);
            String paramBefore = parseSpringElWithCustom(paramBeforeEl, context);
            apiLog.setParamBefore(paramBefore);
        } finally {
            LogRecordContext.clear();
        }
        String operateLog = parseSpringElWithCustom(operateLogEl, context);

        if (StringUtils.isBlank(operateLogEl)) {
            //系统自动生成日志记录
            operateLogEl = buildOperationLog(nameByAccount, type, model, busCode);
        } else {
            operateLogEl = operateLog;
        }
        apiLog.setOperateLog(operateLogEl);

        if (!methodList.contains(EXECUTOR_METHOD)) {
            try {
                operateLogService.operateLogRecord(apiLog);
                //后续操作的通知
                LogResult logResult = new LogResult();
                logResult.setModuleType(model.getValue());
                logResult.setCurrentUserAccount(currentAccount);
                if (StringUtils.isNotBlank(request)) {
                    logResult.setFunctionRequest(request);
                }
                if (result != null) {
                    logResult.setFunctionResult(String.valueOf(result));
                }
                logStrategyService.afterHandler(logResult);
            } catch (Exception e) {
                log.error("日志插入异常", e);
            }
        }
        return result;
    }


    private String parseSpringElWithCustom(String operateLogEl, StandardEvaluationContext context) {
        Expression expression = PARSER.parseExpression(operateLogEl, MY_PARSER_CONTEXT);
        String result = null;
        Object value = expression.getValue(context);
        if (value != null) {
            result = value.toString();
        }
        return result;
    }

    private StandardEvaluationContext getContext(Method method, Object[] args) {
        StandardEvaluationContext context = new StandardEvaluationContext();
        //添加参数
        DefaultParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();
        String[] parameterNames = discoverer.getParameterNames(method);
        for (int i = 0; i < Objects.requireNonNull(parameterNames).length; i++) {
            if (args[i] == null) {
                continue;
            }
            context.setVariable(parameterNames[i], args[i]);
        }
        return context;
    }

    private String parseSpringEl(StandardEvaluationContext context, String busCodeEl) {
        if (StringUtils.isBlank(busCodeEl)) {
            return null;
        }
        Expression busCodeExpression = PARSER.parseExpression(busCodeEl);
        Object expressionValueWithBusCode = busCodeExpression.getValue(context);
        String busCode = null;
        if (expressionValueWithBusCode != null) {
            busCode = expressionValueWithBusCode.toString();
        }
        return busCode;
    }

    public String buildOperationLog(String userName, OperateTypeEnum typeEnum, ModuleTypeEnum moduleTypeEnum, String busCode) {
        String concat = String.format(LOG_STR, userName, moduleTypeEnum.getDesc(), typeEnum.getDesc());
        StringBuilder sb = new StringBuilder();
        sb.append(concat);
        if (StringUtils.isNotBlank(busCode)) {
            sb.append(String.format(BUS_CODE_STR, busCode));
        }
        return sb.toString();
    }

    private static final String LOG_STR = "用户【%s】对模块【%s】进行了【%s】操作。";
    private static final String BUS_CODE_STR = "操作的业务单号【%s】";

}
