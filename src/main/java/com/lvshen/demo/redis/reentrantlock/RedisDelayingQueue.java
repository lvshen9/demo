package com.lvshen.demo.redis.reentrantlock;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

/**
 * Description:redis实现延迟队列
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/4/23 11:00
 * @since JDK 1.8
 */
@Component
@Slf4j
public class RedisDelayingQueue<T> {

    @Autowired
    private RedisTemplate redisTemplate;

    static class TaskItem<T> {
        public String id;
        public T msg;
    }

    private Type taskType = new TypeReference<TaskItem<T>>() {
    }.getType();

    private String queueKey;
/*
    public RedisDelayingQueue(String queueKey) {
        this.queueKey = queueKey;
    }*/

    public void setQueueKey(String queueKey) {
        this.queueKey = queueKey;
    }

    /**
     * 任务放入队列中
     * @param msg    任务信息
     * @param afterTime   延迟时间
     */
    public void delay(T msg, long afterTime) {
        TaskItem<T> task = new TaskItem<>();
        task.id = UUID.randomUUID().toString();
        task.msg = msg;

        String taskStr = JSON.toJSONString(task);
        Date now = new Date();
        log.info(">>>>>>>>>>开始将任务放入到zset中，当前时间：{}", now);
        redisTemplate.opsForZSet().add(queueKey, taskStr, now.getTime() + afterTime);
    }

    public void loop() {
        while (!Thread.interrupted()) {
            Date now = new Date();
            Set<String> values = redisTemplate.opsForZSet().rangeByScore(queueKey, 0, now.getTime(), 0, 1);
            if (values.isEmpty()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            String next = values.iterator().next();
            Long remove = redisTemplate.opsForZSet().remove(queueKey, next);
            if ((remove > 0)) {
                TaskItem<T> taskItem = JSON.parseObject(next, taskType);
                this.handleMsg(taskItem.msg, now);
            }
        }
    }

    private void handleMsg(T msg, Date now) {
        log.info("延迟消费到数据:[{}]，当前时间：[{}]", msg, now);
        System.out.println(String.format("延迟消费到数据:[%s]", msg));
    }
}
