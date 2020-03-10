package com.lvshen.demo.member.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2020/3/7 15:50
 * @since JDK 1.8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    private String id;
    private String name;
    private Integer code;
}
