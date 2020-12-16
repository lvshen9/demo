package com.lvshen.demo.catchexception;

import lombok.Data;
import lombok.ToString;
import org.apache.poi.ss.formula.functions.T;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-12-16 10:28
 * @since JDK 1.8
 */
@Data
@ToString
public class Result<T> {
    private int code;
    private String message = "操作成功";
    private T data;
    private String exception;

}
