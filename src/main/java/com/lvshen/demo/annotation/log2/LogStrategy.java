package com.lvshen.demo.annotation.log2;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2024/3/31 19:13
 * @since JDK 1.8
 */
public interface LogStrategy {
    /**
     * 操作日志记录后的操作
     * @param result
     */
    void afterHandler(LogResult result);

    /**
     * 获取模块类型
     *
     * @return
     */
    String getModuleTypeStr();
}
