package com.lvshen.demo.annotation.validator;

import com.google.common.collect.Lists;
import com.lvshen.demo.annotation.export.ExportExcel;
import com.lvshen.demo.annotation.export.ExportFiled;
import com.lvshen.demo.catchexception.BusinessExceptionAssert;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description: AOP方法增强
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-8-31 15:05
 * @since JDK 1.8
 */
@Component
@Aspect
@Slf4j
public class ValidatorAspect {
    @Pointcut("@annotation(com.lvshen.demo.annotation.validator.ValidatorHandler)")
    public void exportPointcut() {

    }

    @Around("exportPointcut()")
    public void doValidator(ProceedingJoinPoint point) throws Throwable {

        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method;
        method = point.getTarget().getClass().getMethod(signature.getName(), signature.getMethod().getParameterTypes());
        ValidatorHandler annotation = method.getAnnotation(ValidatorHandler.class);
        //1.获取ValidatorHandler注解上validators的值
        Class<?> aClass = annotation.validators();
        Object o = aClass.newInstance();
        BusinessExceptionAssert.checkArgument(o instanceof AbstractValidator,"validators的参数需要为AbstractValidator类型");
        AbstractValidator validator = (AbstractValidator) o;
        Object[] args = point.getArgs();
        //2.获取方法第一个参数值
        Object object = args[0];
        validator.check(object);
        //3.执行当前方法
        point.proceed();
    }

}
