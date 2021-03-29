package com.lvshen.demo.annotation.log;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.lang.Snowflake;
import com.google.common.base.CaseFormat;
import com.lvshen.demo.annotation.log.entity.Operation;
import com.lvshen.demo.annotation.log.service.OperationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

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
@Slf4j
public class OperationLogAspect {

    @Autowired
    private OperationService operationService;

    private static Snowflake snowflake =  new Snowflake(1,1);

    @Autowired
    HttpServletRequest request;

    @Around("@annotation(com.lvshen.demo.annotation.log.OperationLog)")
    public Object log(ProceedingJoinPoint pjp) throws Exception {

        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        //获取目标方法上面的注解
        OperationLog opLog = method.getAnnotation(OperationLog.class);
        Object result;
        TimeInterval timer = DateUtil.timer();
        try {
            // 执行目标方法
            result = pjp.proceed();
        } catch (Throwable throwable) {
            throw new Exception(throwable);
        }
        long executeTime = timer.intervalRestart();

        if (StringUtils.isNotEmpty(opLog.opBusinessId())) {

            SpelExpressionParser parser = new SpelExpressionParser();
            Expression expression = parser.parseExpression(opLog.opBusinessId());
            EvaluationContext context = new StandardEvaluationContext();

            // 获取参数值
            Object[] args = pjp.getArgs();
            // 获取运行时参数的名称
            LocalVariableTableParameterNameDiscoverer discoverer
                    = new LocalVariableTableParameterNameDiscoverer();
            String[] parameterNames = discoverer.getParameterNames(method);

            // 将参数绑定到context中
            if (parameterNames != null) {
                for (int i = 0; i < parameterNames.length; i++) {
                    context.setVariable(parameterNames[i], args[i]);
                }
            }

            // 将方法的result当做变量放到context中，变量名称为该类名转化为小写字母开头的驼峰形式
            if (result != null) {
                context.setVariable(
                        CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, result.getClass().getSimpleName()),
                        result);
            }
            // 解析表达式，获取结果
            String itemId = String.valueOf(expression.getValue(context));
            // 执行日志记录
            Operation operation = Operation.builder()
                    //.id(snowflake.nextId())
                    .opType(opLog.opType().name())
                    .opBusinessName(opLog.opBusinessName())
                    .opBusinessId(itemId)
                    .opTime(String.valueOf(executeTime))
                    //这里可以添加操作人
                    .build();
            handle(operation);
        }
        return result;
    }


    private void handle(Operation operation) {
        // 通过日志打印输出,如果有需求可以创建一个operation_log表存入数据库
        log.info("opType = " + operation.getOpType() + ",opItem = " + operation.getOpBusinessName() + ",opItemId = " + operation.getOpBusinessId() + ",opTime = " + operation.getOpTime());
        // 持久化入库
        operationService.save(operation);
    }

    private String generatorId() {
        long l = snowflake.nextId();
        return String.valueOf(l);
    }
}

