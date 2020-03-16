package com.lvshen.demo.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/3/16 14:25
 * @since JDK 1.8
 */
@Component
@Aspect
public class SetFieldValueAspect {

    @Autowired
    BeanUtil beanUtil;

    @Around("@annotation(com.lvshen.demo.annotation.NeedSetValueField)")
    public Object doSetFieldValue(ProceedingJoinPoint point) throws Throwable {
        Object o = point.proceed();

        if (o instanceof Collection) {
            this.beanUtil.setFieldValueForCol(((Collection) o));
        } else {
            //返回结果不是集合的逻辑
        }
        return o;
    }
}
