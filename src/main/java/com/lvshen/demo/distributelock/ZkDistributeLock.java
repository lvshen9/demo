package com.lvshen.demo.distributelock;

import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/3/15 21:53
 * @since JDK 1.8
 */
@Slf4j
public class ZkDistributeLock implements Lock {

    @Autowired
    private ZookeeperConfig config;

    private String lockPath;

    private ZkClient client;

    private String currentPath;

    private String beforePath;

    public ZkDistributeLock(String lockPath) {
        super();
        this.lockPath = lockPath;
        ZookeeperConfig zookeeperConfig = new ZookeeperConfig();
        this.client = zookeeperConfig.getConnectionWithoutSpring();
        this.client.setZkSerializer(new MyZkSerializer());

        if (!this.client.exists(lockPath)) {
            try {
                this.client.createPersistent(lockPath);
            } catch (ZkNodeExistsException e) {
                e.getStackTrace();
            }
        }
    }

    public void setLockPath(String lockPath) {
        this.lockPath = lockPath;
        // this.client = config.getConnection();

        ZookeeperConfig zookeeperConfig = new ZookeeperConfig();
        this.client = zookeeperConfig.getConnectionWithoutSpring();
    }

    @Override
    public void lock() {

        if (!tryLock()) {
            //没获得锁，阻塞自己
            waitForLock();
            //再次尝试
            lock();
        }

    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {  //不会阻塞
        //创建节点
        if (this.currentPath == null) {
            currentPath = this.client.createEphemeralSequential(lockPath + "/", "lock");
        }
        List<String> children = this.client.getChildren(lockPath);
        Collections.sort(children);

        if (currentPath.equals(lockPath + "/" + children.get(0))) {
            return true;
        } else {
            int currentIndex = children.indexOf(currentPath.substring(lockPath.length() + 1));
            beforePath = lockPath + "/" + children.get(currentIndex - 1);
        }

        log.info("锁节点创建成功：{}", lockPath);
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        client.delete(this.currentPath);
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    private void waitForLock() {
        CountDownLatch count = new CountDownLatch(1);
        IZkDataListener listener = new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {

            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                System.out.println(String.format("收到节点[%s]被删除了",s));
                count.countDown();
            }
        };

        client.subscribeDataChanges(this.beforePath, listener);

        //自己阻塞自己
        if (this.client.exists(this.beforePath)) {
            try {
                count.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //取消注册
        client.unsubscribeDataChanges(this.beforePath, listener);
    }
}
