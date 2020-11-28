package com.lvshen.demo.guava.study.cache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-11-25 16:54
 * @since JDK 1.8
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Province {
    //省会名称
    private String name;
    //省会编码 如：PR001
    private String code;
}
