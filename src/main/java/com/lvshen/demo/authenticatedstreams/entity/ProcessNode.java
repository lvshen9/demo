package com.lvshen.demo.authenticatedstreams.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
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
@TableName(value = "as_node")
@Data
@EqualsAndHashCode(callSuper = false)
public class ProcessNode implements Serializable {
    /**
     * id:
     */
    @TableId
    private String id;

    /**
     * flow_code:审核流编号
     */
    @TableField(value = "code")
    private String code;

    /**
     * level:审核层级
     */
    @TableField(value = "level")
    private Byte level;

    /**
     * approve_man:审核人
     */
    @TableField(value = "approve_man")
    private String approveMan;

    /**
     * level_desc:审核层级描述
     */
    @TableField(value = "level_desc")
    private String levelDesc;

    private static final long serialVersionUID = 1L;

 }
