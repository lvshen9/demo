package com.lvshen.demo.member.entity;

import cn.hutool.core.date.DateUtil;
import com.lvshen.demo.jsoup.utils.DateUtils;
import lombok.Data;

import java.util.Date;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-11-2 10:44
 * @since JDK 1.8
 */
@Data
public class Activity {
    private String id;
    private Date startDate;
    private Date endDate;
    private String status;

    public String validateStatus(Date currentDate) {
        if (DateUtils.beforeDate(currentDate,startDate)) {
            this.status = "未开始";
        }
        if (DateUtils.betweenDate(currentDate,startDate,endDate)) {
            this.status = "进行中";
        }
        if (DateUtils.afterDate(currentDate,endDate)) {
            this.status = "已结束";
        }
        return status;
    }

}
