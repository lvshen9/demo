package com.lvshen.demo.zookeeperdemo;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.CountDownLatch;


/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/3/16 12:00
 * @since JDK 1.8
 */
@Configuration
@Slf4j
public class ZkCfg {

    @Value("${zookeeper.address}")
    private String connectString;

    @Value("${zookeeper.timeout}")
    private int timeout;

    @Value("${zookeeper.enabled}")
    private boolean enabled;

    @Bean(name = "zkClient")
    public ZooKeeper zkClient() {
        ZooKeeper zooKeeper = null;
        if (!enabled) {
            return null;
        }
        try {
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            //连接成功后，会回调watcher监听，此连接操作是异步的，执行完new语句后，直接调用后续代码
            //  可指定多台服务地址 127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183
            zooKeeper = new ZooKeeper(connectString, timeout, event -> {
                if (Watcher.Event.KeeperState.SyncConnected == event.getState()) {
                    //如果收到了服务端的响应事件,连接成功
                    countDownLatch.countDown();
                }
            });
            countDownLatch.await();
            log.info("【初始化ZooKeeper连接状态....】={}", zooKeeper.getState());

        } catch (Exception e) {
            log.error("初始化ZooKeeper连接异常....】={}", e);
        }
        return zooKeeper;
    }


}
