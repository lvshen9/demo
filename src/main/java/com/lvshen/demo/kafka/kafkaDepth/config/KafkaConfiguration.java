package com.lvshen.demo.kafka.kafkaDepth.config;

import com.lvshen.demo.kafka.kafkaDepth.properties.KafkaConfigProperties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/12/5 9:21
 * @since JDK 1.8
 */
//@Component("kafkaConfigurations")
//@EnableKafka
public class KafkaConfiguration {
    @Autowired
    private KafkaConfigProperties kafkaConfigProperties;
    /**
     * ConcurrentKafkaListenerContainerFactory为创建Kafka监听器的工程类，这里只配置了消费者
     */
	@Bean
	public ConcurrentKafkaListenerContainerFactory<Integer, String> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<Integer, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		factory.getContainerProperties().setPollTimeout(1500);
		factory.setMissingTopicsFatal(false);
		return factory;
	}

    /**
     * 根据consumerProps填写的参数创建消费者工厂
     */
	@Bean
	public ConsumerFactory<Integer, String> consumerFactory() {
		return new DefaultKafkaConsumerFactory<>(consumerProps());
	}

    /**
     * 根据senderProps填写的参数创建生产者工厂
     */
	@Bean
	public ProducerFactory<Integer, String> producerFactory() {
		return new DefaultKafkaProducerFactory<>(senderProps());
	}

    /**
     * kafkaTemplate实现了Kafka发送接收等功能
     */
	@Bean("kafkaTemplates")
	public KafkaTemplate<Integer, String> kafkaTemplate() {
		KafkaTemplate template = new KafkaTemplate<>(producerFactory());
		return template;
	}

    /**
     * 消费者配置参数
     */
	private Map<String, Object> consumerProps() {
		Map<String, Object> props = new HashMap<>();
		// 连接地址
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfigProperties.getBootstrapServers());
		// GroupID
		props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConfigProperties.getGroupId());
		// 是否自动提交
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
		// 自动提交的频率
		props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, kafkaConfigProperties.getAutoCommitIntervalMs());
		// Session超时设置
		props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, kafkaConfigProperties.getSessionTimeoutMs());
		// 键的反序列化方式
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
		// 值的反序列化方式
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		return props;
	}

    /**
     * 生产者配置
     */
	private Map<String, Object> senderProps() {
		Map<String, Object> props = new HashMap<>();
		// 连接地址
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfigProperties.getBootstrapServers());
		// 重试，0为不启用重试机制
		props.put(ProducerConfig.RETRIES_CONFIG, kafkaConfigProperties.getRetries());
		// 控制批处理大小，单位为字节
		props.put(ProducerConfig.BATCH_SIZE_CONFIG, kafkaConfigProperties.getBatchSize());
		// 批量发送，延迟为1毫秒，启用该功能能有效减少生产者发送消息次数，从而提高并发量
		props.put(ProducerConfig.LINGER_MS_CONFIG, kafkaConfigProperties.getLingerMs());
		// 生产者可以使用的总内存字节来缓冲等待发送到服务器的记录
		props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, kafkaConfigProperties.getBufferMemory());
		// 键的序列化方式
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
		// 值的序列化方式
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		return props;
	}
}
