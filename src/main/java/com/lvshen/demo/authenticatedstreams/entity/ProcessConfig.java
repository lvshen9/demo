package com.lvshen.demo.authenticatedstreams.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 流程审核
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-5-7 11:07
 * @since JDK 1.8
 */
@TableName(value = "as_config")
@Data
@EqualsAndHashCode(callSuper = false)
public class ProcessConfig implements Serializable {
    /**
     * id:
     */
    @TableId(value = "id")
    private String id;

    /**
     * flow_code:审核流编号
     */
    @TableField(value = "code")
    private String code;

    /**
     * function_code:功能模块编号
     */
    @TableField(value = "module_code")
    private String moduleCode;

    /**
     * levels:审核层级数
     */
    @TableField(value = "levels")
    private Byte levels;

    /**
     * times:发起次数
     */
    @TableField(value = "times")
    private Byte times;

    /**
     * status:状态 0-无效 1-有效
     */
    @TableField(value = "status")
    private String status;

    /**
     * deleted:是否删除
     */
    @TableField(value = "deleted")
    private Byte deleted;

    /**
     * created_by:创建人
     */
    @TableField(value = "created_by")
    private String createdBy;

    /**
     * created_time:创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "created_time")
    private Date createdTime;

    /**
     * updated_by:更新人
     */
    @TableField(value = "updated_by")
    private String updatedBy;

    /**
     * updated_time:更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "updated_time")
    private Date updatedTime;


    private static final long serialVersionUID = 1L;

 }
