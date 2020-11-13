package com.lvshen.demo.annotation.log.dao;

import com.lvshen.demo.annotation.log.entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-11-13 9:30
 * @since JDK 1.8
 */
@Repository
public interface OperationDao extends JpaRepository<Operation,String> {

}
