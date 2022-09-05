package com.lvshen.demo.logexception.apilog;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-9-5 15:23
 * @since JDK 1.8
 */
public interface RetryResultHandler {
    /**
     * 方法结果处理器
     * @param obj
     * @return
     */
    String resultHandler(Object obj);

    /**
     * 该方法的父层调用方法
     * @return
     */
    String invokeMethodStr();

    /**
     * Enable注解标注的方法名称
     * @return
     */
    String methodName();
}
