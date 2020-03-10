package com.lvshen.demo.kafka.kafkaDepth.listen;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/12/5 9:53
 * @since JDK 1.8
 */
@Component
@Slf4j
public class DemoListener {
    /**
     * 声明consumerID为demo，监听topicName为topic.quick.demo的Topic
     */
    @KafkaListener(id = "demo", topics = "topic.quick.demo")
    public void listen(String msgData) {
        log.info("demo receive : "+msgData);
    }

}
