package com.lvshen.demo.kafka.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/12/5 13:54
 * @since JDK 1.8
 */
@Component
@Slf4j
public class KafkaConsumer {
    @KafkaListener(topics = "${spring.kafka.topic}")
    public void listen(ConsumerRecord<?, ?> record) {
        log.info("topic={}, offset={}, message={}", record.topic(), record.offset(), record.value());
    }

}
