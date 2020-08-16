package com.lvshen.demo.kafka.kafkaDepth.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

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
@Getter
@Setter
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
