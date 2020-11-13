package com.lvshen.demo.annotation.log.service;

import com.lvshen.demo.annotation.log.dao.OperationDao;
import com.lvshen.demo.annotation.log.entity.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-11-13 9:33
 * @since JDK 1.8
 */
@Service
public class OperationService {
    @Autowired
    private OperationDao operationDao;

    public void save(Operation operation) {
        operationDao.save(operation);
    }
}
