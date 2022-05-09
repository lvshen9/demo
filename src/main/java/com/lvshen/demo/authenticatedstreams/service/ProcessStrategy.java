package com.lvshen.demo.authenticatedstreams.service;

import com.lvshen.demo.authenticatedstreams.praram.ProcessResult;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-5-9 13:58
 * @since JDK 1.8
 */
public interface ProcessStrategy {
    /**
     * 审批流程结束后的操作
     *
     * @param result
     */
    void afterProcessOperation(ProcessResult result);

    /**
     * 下一个流程的操作
     *
     * @param result
     */
    void nextProcessOperation(ProcessResult result);

    /**
     * 获取模块类型
     *
     * @return
     */
    String getModuleTypeStr();
}
