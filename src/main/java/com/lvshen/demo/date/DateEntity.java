package com.lvshen.demo.date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/6/10 22:04
 * @since JDK 1.8
 */
@Data
public class DateEntity {

    private Date startDate;

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

    public DateEntity() {
    }

    public DateEntity(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        //return (Date) endDate.clone();
        // return new Date(endDate.getTime());
        return endDate;
    }

    public void setEndDate(Date endDate) {
        //this.endDate = (Date) endDate.clone();
        // this.endDate = new Date(endDate.getTime());
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "DateEntity{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
