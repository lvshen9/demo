package com.lvshen.demo.authenticatedstreams.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedBy;

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
@TableName(value = "as_process")
@Data
@EqualsAndHashCode(callSuper = false)
public class ProcessRecord implements Serializable {
    /**
     * id:id
     */
    @TableId
    private Long id;

    /**
     * flow_code:审核流编号
     */
    @TableField(value = "code")
    private String code;

    /**
     * level:当前审核层级数
     */
    @TableField(value = "level")
    private Byte level;

    /**
     * sheet_no:单据号
     */
    @TableField(value = "sheet_no")
    private String sheetNo;

    /**
     * times:第几次审核
     */
    @TableField(value = "times")
    private Byte times;

    /**
     * approve_man:审核人OA账号
     */
    @TableField(value = "approve_man")
    private String approveMan;

    /**
     * approve_time:审核时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "approve_time")
    private Date approveTime;

    /**
     * status:状态；0=审核中，1=通过，2=拒绝
     */
    @TableField(value = "status")
    private Byte status;

    /**
     * memo:审核意见
     */
    @TableField(value = "memo")
    private String memo;

    @TableField(value = "deleted")
    private Byte deleted;

    @TableField(value = "created_by")
    private String createdBy;

    @TableField(value = "created_time")
    private Date createdTime;

    @TableField(value = "updated_by")
    private String updatedBy;

    @TableField(value = "updated_time")
    private Date updatedTime;



    public void initLevel() {
        this.level = 1;
    }

    private static final long serialVersionUID = 1L;

    public void unDeleted() {
        this.deleted = 0;
    }
}
