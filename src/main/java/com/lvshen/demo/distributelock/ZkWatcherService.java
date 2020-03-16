package com.lvshen.demo.distributelock;

import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/3/15 20:17
 * @since JDK 1.8
 */
@Component
@Slf4j
public class ZkWatcherService {

    @Autowired
    private ZookeeperConfig zookeeperConfig;

    /**
     * node监听
     * @param path
     */
    public void watcherNode(String path) {
        ZkClient connection = zookeeperConfig.getConnection();
        connection.subscribeDataChanges(path, new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                log.info("路径：{}收到了节点变化：{}", dataPath, data);
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                log.info("路径：{}收到节点删除");
            }
        });
    }

}
