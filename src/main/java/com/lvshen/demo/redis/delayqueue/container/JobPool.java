package com.lvshen.demo.redis.delayqueue.container;

import com.alibaba.fastjson.JSON;
import com.lvshen.demo.redis.delayqueue.bean.Job;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


/**
 * 任务池
 *
 * @author daify
 * @date 2019-07-26 14:38
 **/
@Component
@Slf4j
public class JobPool {

    @Autowired
    private RedisTemplate redisTemplate;

    private String NAME = "job.pool";

    private BoundHashOperations getPool() {
        BoundHashOperations ops = redisTemplate.boundHashOps(NAME);
        return ops;
    }

    /**
     * 添加任务
     *
     * @param job
     */
    public void addJob(Job job) {
        log.info("任务池添加任务：{}", JSON.toJSONString(job));
        getPool().put(job.getId(), job);
        return;
    }

    /**
     * 获得任务
     *
     * @param jobId
     * @return
     */
    public Job getJob(Long jobId) {
        Object o = getPool().get(jobId);
        Job job = new Job();
        if (o != null) {
            BeanUtils.copyProperties(o, job);
        }
        return job;

    }

    /**
     * 移除任务
     *
     * @param jobId
     */
    public void removeDelayJob(Long jobId) {
        log.info("任务池移除任务：{}", jobId);
        // 移除任务
        getPool().delete(jobId);
    }
}
