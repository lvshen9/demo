package com.lvshen.demo.function;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2021-11-29 11:01
 * @since JDK 1.8
 */
@FunctionalInterface
public interface BranchHandler {

    void trueOrFalseHandle(Runnable trueHandle, Runnable falseHandle);
}
