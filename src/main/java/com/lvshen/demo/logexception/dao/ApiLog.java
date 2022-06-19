package com.lvshen.demo.logexception.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author lvshen
 * @Description sys_api_log:api请求日志记录表
 * @date 2022-05-18
 */
@Table(name = "sys_api_log")
@Data
@EqualsAndHashCode(callSuper = false)
public class ApiLog implements Serializable {
    /**
     * id:
     */
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * model:类型 IN-本系统对外api or OUT-调的外部接口
     */
    @Column(name = "model")
    private String model;

    /**
     * current_system:接口调用的系统名称
     */
    @Column(name = "current_system")
    private String currentSystem;

    /**
     * req_type:请求类别，QUERY，INSERT，UPDATE，DELETE
     */
    @Column(name = "req_type")
    private String reqType;

    /**
     * provider:接口提供方
     */
    @Column(name = "provider")
    private String provider;

    /**
     * url:请求接口路径
     */
    @Column(name = "url")
    private String url;

    /**
     * request:请求信息
     */
    @Column(name = "request")
    private String request;

    /**
     * result:返回结果
     */
    @Column(name = "result")
    private String result;

    /**
     * ip_addr:请求的ip地址
     */
    @Column(name = "ip_addr")
    private String ipAddr;

    /**
     * is_exception:系统调用是否异常，1-是，0-否
     */
    @Column(name = "is_exception")
    private String isException;

    /**
     * success_str:成功标识
     */
    @Column(name = "success_str")
    private String successStr;

    /**
     * deleted:是否删除
     */
    @Column(name = "deleted")
    private Byte deleted;

    /**
     * created_by:创建人
     */
    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;

    /**
     * param_type_name:参数类型
     */
    @Column(name = "param_type_name")
    private String paramTypeName;

    /**
     * route_name:路由地址
     */
    @Column(name = "route_name")
    private String routeName;

    /**
     * created_time:创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "created_time")
    private Date createdTime;

    /**
     * updated_by:更新人
     */
    @Column(name = "updated_by")
    private String updatedBy;

    /**
     * updated_time:更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "updated_time")
    private Date updatedTime;

    private static final long serialVersionUID = 1L;

    public void unDeleted() {
        this.deleted = 0;
    }

    public void hasDeleted() {
        this.deleted = 1;
    }
}
