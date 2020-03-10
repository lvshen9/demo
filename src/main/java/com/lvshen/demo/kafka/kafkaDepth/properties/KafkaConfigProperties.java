package com.lvshen.demo.kafka.kafkaDepth.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/11/28 16:13
 * @since JDK 1.8
 */

@Component
@ConfigurationProperties(prefix="kafka")
@PropertySource(value = {"classpath:config/kafka-config.properties"}, encoding = "utf-8")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KafkaConfigProperties {
    private String bootstrapServers;
    private String groupId;
    private String enableAutoCommit;
    private String autoCommitIntervalMs;
    private String sessionTimeoutMs;
    private String retries;
    private String batchSize;
    private String lingerMs;
    private String bufferMemory;

}
