package com.lvshen.demo.distributelock;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/3/15 20:41
 * @since JDK 1.8
 */
@Configuration
@Data
@Slf4j
public class ZookeeperConfig {

    @Value("${zookeeper.address}")
    private String address;

    @Value("${zookeeper.timeout}")
    private int timeout;

    public ZkClient getConnection() {
        ZkClient zkClient = new ZkClient(address);
        log.info("Zookeeper 开始连接.....");
        zkClient.setZkSerializer(new MyZkSerializer());
        return zkClient;
    }

    public ZkClient getConnectionWithoutSpring() {
        String addr = "192.168.42.128:2181";
        ZkClient zkClient = new ZkClient(addr);
        log.info("Zookeeper 开始连接.....");
        zkClient.setZkSerializer(new MyZkSerializer());
        return zkClient;
    }
}
