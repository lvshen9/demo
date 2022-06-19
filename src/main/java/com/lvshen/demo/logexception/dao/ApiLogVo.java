package com.lvshen.demo.logexception.dao;

import lombok.Data;

/**
 * @author lvshen
 * @Description api请求日志记录表Vo类
 * @date 2022/05/18
 */
@Data
public class ApiLogVo extends ApiLog {
    private String createdByName;
    /**
     * 系统名称
     */
    private String sysName;

    /**
     * 接口路由地址
     */
    private String serviceId;

    /**
     * 接口路由路径
     */
    private String srmRouteName;

}
