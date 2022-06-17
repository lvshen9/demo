package com.lvshen.demo.annotation.log.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

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
public class Operation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    private String opType;
    private String opBusinessName;
    private String opBusinessId;
    private String opTime;
    private String opUser;
}
