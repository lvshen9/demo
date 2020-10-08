package com.lvshen.demo.annotation.log;

import lombok.Data;

import java.util.Date;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/10/8 11:27
 * @since JDK 1.8
 */
@Data
public class ExceptionLog {
    private String id;
    private String module;
    private String type;
    private String desc;

    private String reqParam;
    private String respParam;
    private String userId;
    private String userName;
    private String exceptionName;
    private String exceptionMessage;
    private String ip;
    private String uri;
    private Date creatTime;
    private String ver;
    private String method;
}
