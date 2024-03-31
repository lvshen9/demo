package com.lvshen.demo.annotation.log2;

import lombok.Data;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2024/3/31 19:12
 * @since JDK 1.8
 */
@Data
public class LogResult {
    /**
     * 业务模块
     */
    private String moduleType;

    /**
     * 当前用户账号
     */
    private String currentUserAccount;

    /**
     * 注解标注方法的请求信息-操作
     */
    private String functionRequest;

    /**
     * 注解标注方法的返回结果
     */
    private String functionResult;
}
