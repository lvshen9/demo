package com.lvshen.demo.member.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Description  sign:签到
 * @author   lvshen
 * @date   2020-09-15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sign {
    /**
     * id:主键id
     */
    private String id;

    /**
     * user_id:用户id
     */
    private String userId;

    /**
     * sign_date:签到时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date signDate;
}