package com.lvshen.demo.redis.delayqueue.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 延迟任务
 * @author daify
 * @date 2019-07-25 15:24
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DelayJob implements Serializable {


    /**
     * 延迟任务的唯一标识
     */
    private long jodId;
    
    /**
     * 任务的执行时间
     */
    private long delayDate;

    /**
     * 任务类型（具体业务类型）
     */
    private String topic;


    public DelayJob(Job job) {
        this.jodId = job.getId();
        Date now = new Date();
        this.delayDate = now.getTime() + job.getDelayTime();
        this.topic = job.getTopic();
    }

    public DelayJob(Object value, Double score) {
        this.jodId = Long.parseLong(String.valueOf(value));
        this.delayDate = System.currentTimeMillis() + score.longValue();
    }

}
