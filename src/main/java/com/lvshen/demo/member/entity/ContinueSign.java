package com.lvshen.demo.member.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description  continue_sign:连续签到统计
 * @author   lvshen
 * @date   2020-09-15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContinueSign {
    /**
     * id:主键id
     */
    private String id;

    /**
     * user_id:用户id
     */
    private String userId;

    /**
     * continue_counts:连续签到次数
     */
    private Integer continueCounts;
}