package com.lvshen.demo.annotation.log2;

import com.lvshen.demo.authenticatedstreams.service.ModuleTypeEnum;
import lombok.Data;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2024/3/31 19:23
 * @since JDK 1.8
 */
@Data
public class OperateLogAddParam {
    /**
     * operate_type:操作类别: QUERY-查看，INSERT-新增，UPDATE-修改，DELETE-删除，UPLOAD-上传，DOWNLOAD-下载，LOGIN-登录，IMPORT-导入，EXPORT-导出
     */
    private OperateTypeEnum operateTypeEnum;

    /**
     * provider:接口提供方
     */
    private String provider;

    /**
     * url:请求接口路径
     */
    private String url;

    /**
     * param_type_name:参数类型
     */
    private String paramTypeName;

    /**
     * bus_module:操作模块
     */
    private ModuleTypeEnum moduleTypeEnum;

    /**
     * bus_code:业务单号
     */
    private String busCode;

    /**
     * remark:业务模块描述
     */
    private String remark;

    /**
     * operate_log:操作记录
     */
    private String operateLog;

    /**
     * invoke_method:该方法的父层方法
     */
    private String invokeMethod;

    /**
     * param_before:操作前入参
     */
    private String paramBefore;

    /**
     * request:请求信息-操作
     */
    private String request;

    /**
     * result:返回结果
     */
    private String result;

    /**
     * operate_account:操作人账号，非必填
     */
    private String operateAccount;

    /**
     * operate_name:操作人姓名，非必填
     */
    private String operateName;
}
