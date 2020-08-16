package com.lvshen.demo.kafka.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/12/5 13:45
 * @since JDK 1.8
 */
@Component
@Slf4j
public class KafkaProducer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Value("${spring.kafka.topic}")
    private String topic;

    /**
     * 发送kafka消息
     *
     * @param jsonString
     */
    public void send(String jsonString) {
        ListenableFuture future = kafkaTemplate.send(topic, jsonString);
        future.addCallback(o -> log.info("kafka消息发送成功：" + jsonString), throwable -> log.error("kafka消息发送失败：" + jsonString));
    }

}
