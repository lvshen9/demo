package com.lvshen.demo.authenticatedstreams.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lvshen.demo.authenticatedstreams.entity.ProcessRecord;
import com.lvshen.demo.authenticatedstreams.mapper.ProcessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * Description:多线程更新
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-7-21 16:31
 * @since JDK 1.8
 */
public class ProcessRecordTransactionThread {
    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;
    @Autowired
    private TransactionDefinition transactionDefinition;
    @Autowired
    private ProcessMapper processMapper;
    @Autowired
    private PlatformTransactionManager transactionManager;

    List<TransactionStatus> transactionStatuses = Collections.synchronizedList(new ArrayList<>());

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void updateProcessWithThreadsAndTrans() throws InterruptedException {
        //查询总数据
        QueryWrapper<ProcessRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("status", Arrays.asList("1", "2"));
        List<ProcessRecord> list = processMapper.selectList(queryWrapper);

        // 线程数量
        final int threadCount = 2;

        //每个线程处理的数据量
        final int dataPartitionLength = (list.size() + threadCount - 1) / threadCount;

        // 创建多线程处理任务
        ExecutorService processThreadPool = Executors.newFixedThreadPool(threadCount);
        CountDownLatch threadLatches = new CountDownLatch(threadCount);
        AtomicBoolean isError = new AtomicBoolean(false);
        try {
            for (int i = 0; i < threadCount; i++) {
                // 每个线程处理的数据
                List<ProcessRecord> threadDataList = list.stream()
                        .skip(i * dataPartitionLength).limit(dataPartitionLength).collect(Collectors.toList());
                processThreadPool.execute(() -> {
                    try {
                        try {
                            updateStudentsTransaction(transactionManager, transactionStatuses, threadDataList);
                        } catch (Throwable e) {
                            e.printStackTrace();
                            isError.set(true);
                        } finally {
                            threadLatches.countDown();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        isError.set(true);
                    }
                });
            }

            // 倒计时锁设置超时时间 30s
            boolean await = threadLatches.await(30, TimeUnit.SECONDS);
            // 判断是否超时
            if (!await) {
                isError.set(true);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            isError.set(true);
        }

        if (!transactionStatuses.isEmpty()) {
            if (isError.get()) {
                transactionStatuses.forEach(s -> transactionManager.rollback(s));
            } else {
                transactionStatuses.forEach(s -> transactionManager.commit(s));
            }
        }

        System.out.println("主线程完成");
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void updateStudentsTransaction(PlatformTransactionManager transactionManager, List<TransactionStatus> transactionStatuses, List<ProcessRecord> processRecords) {
        // 使用这种方式将事务状态都放在同一个事务里面
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务，这样会比较安全些。
        TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
        transactionStatuses.add(status);

        processRecords.forEach(s -> {
            // 更新教师信息
            s.setStatus((byte) 3);
            processMapper.updateById(s);
        });
        System.out.println("子线程：" + Thread.currentThread().getName());
    }

    public void batchUpdate() {
        QueryWrapper<ProcessRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("status", Arrays.asList("1", "2"));
        List<ProcessRecord> list = processMapper.selectList(queryWrapper);

        for (ProcessRecord record : list) {
            record.setCode("xxx");
            processMapper.updateById(record);
        }
    }

    /**
     * 手动提交事务
     */
    public void batchUpdateWithHandTransaction() {
        QueryWrapper<ProcessRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("status", Arrays.asList("1", "2"));
        List<ProcessRecord> list = processMapper.selectList(queryWrapper);

        TransactionStatus transaction = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try {
            for (ProcessRecord record : list) {
                record.setCode("xxx");
                processMapper.updateById(record);
            }
            dataSourceTransactionManager.commit(transaction);
        } catch (Throwable throwable) {
            dataSourceTransactionManager.rollback(transaction);
            throw throwable;
        }
    }

}
