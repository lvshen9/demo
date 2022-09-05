package com.lvshen.demo.logexception.apilog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022/5/18 16:11
 * @since JDK 1.8
 */
@Getter
@AllArgsConstructor
public enum OperationType {
    QUERY("查询", "QUERY"),
    INSERT("新增", "INSERT"),
    UPDATE("更新", "UPDATE"),
    DELETE("删除", "DELETE");


    private String desc;
    private String value;
}
