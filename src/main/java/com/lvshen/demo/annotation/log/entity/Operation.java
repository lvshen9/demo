package com.lvshen.demo.annotation.log.entity;

import com.lvshen.demo.annotation.log.OperationType;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-11-13 9:05
 * @since JDK 1.8
 */
@Data
@Builder
@Table
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Operation {
    @Id
    @GeneratedValue
    private String id;
    private OperationType opType;
    private String opBusinessName;
    private String opBusinessId;
    private long opTime;
    private String opUser;
}
