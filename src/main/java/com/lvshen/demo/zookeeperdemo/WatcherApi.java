package com.lvshen.demo.zookeeperdemo;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/3/16 12:09
 * @since JDK 1.8
 */
@Slf4j
public class WatcherApi implements Watcher {
    @Override
    public void process(WatchedEvent watchedEvent) {
        log.info("【Watcher监听事件】={}",watchedEvent.getState());
        log.info("【监听路径为】={}",watchedEvent.getPath());
        log.info("【监听的类型为】={}",watchedEvent.getType()); //  三种监听类型： 创建，删除，更新
    }
}
