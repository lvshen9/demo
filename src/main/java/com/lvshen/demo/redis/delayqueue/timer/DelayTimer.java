package com.lvshen.demo.redis.delayqueue.timer;

import com.lvshen.demo.redis.delayqueue.container.DelayBucket;
import com.lvshen.demo.redis.delayqueue.container.JobPool;
import com.lvshen.demo.redis.delayqueue.container.ReadyQueue;
import com.lvshen.demo.redis.delayqueue.handler.DelayJobHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 *
 * @author daify
 * @date 2019-08-08 14:15
 **/
@Component
public class DelayTimer implements ApplicationListener <ContextRefreshedEvent> {

    @Autowired
    private DelayBucket delayBucket;
    @Autowired
    private JobPool jobPool;
    @Autowired
    private ReadyQueue readyQueue;
    
    @Value("${thread.size}")
    private int length;
    
    @Override 
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        ExecutorService executorService = new ThreadPoolExecutor(
                length, 
                length,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue <Runnable>());

        for (int i = 0; i < length; i++) {
            executorService.execute(
                    new DelayJobHandler(
                            delayBucket,
                            jobPool,
                            readyQueue,
                            i));
        }
        
    }
}
