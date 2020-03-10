package com.lvshen.demo;

import java.util.Collection;

import javax.annotation.Resource;

import com.lvshen.demo.kafka.kafka.producer.KafkaProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.listener.adapter.RecordMessagingMessageListenerAdapter;
import org.springframework.test.context.junit4.SpringRunner;


import lombok.extern.slf4j.Slf4j;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/11/29 9:41
 * @since JDK 1.8
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class KafkaTest {

	@Autowired
	private KafkaProducer kafkaProducer;

	@Resource
	private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

	@Resource(name = "kafkaTemplates")
	private KafkaTemplate kafkaTemplate;

	@Test
	public void testDemo() throws InterruptedException {
		log.info("start send");
		kafkaProducer.send("中文测试222");
		log.info("end send");
		// 休眠10秒，为了使监听器有足够的时间监听到topic的数据
		Thread.sleep(10);
	}

	@Test
	public void testDemoDepth() throws InterruptedException {
		log.info("start send");
		kafkaTemplate.send("topic.quick.demo", "this is a test for depth kafka");
		log.info("end send");
		// 休眠10秒，为了使监听器有足够的时间监听到topic的数据
		Thread.sleep(500000);
	}

	@Test
	public void testListener() {
		Collection<MessageListenerContainer> listenerContainers = kafkaListenerEndpointRegistry.getListenerContainers();
		for (MessageListenerContainer listenerContainer : listenerContainers) {
			ContainerProperties containerProperties = listenerContainer.getContainerProperties();
            String[] topics = containerProperties.getTopics();
            RecordMessagingMessageListenerAdapter listener = (RecordMessagingMessageListenerAdapter) containerProperties
					.getMessageListener();

		}
		log.info("listenerContainers:{}", listenerContainers);
	}

}
