package com.lvshen.demo.logexception.dao;

import lombok.Data;

import java.util.Date;

/**
 * @author lvshen
 * @Description api请求日志记录表QueryPram类
 * @date 2022/05/18
 */
@Data
public class ApiLogQueryParam extends ApiLog {
    /**
     * 开始日期
     */
    private Date createdTimeStart;

    /**
     * 结束时间
     */
    private Date createdTimeEnd;
}
