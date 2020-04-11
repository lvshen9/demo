package com.lvshen.demo.autoidempotent;

import lombok.Data;

import java.io.Serializable;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/4/10 21:33
 * @since JDK 1.8
 */
@Data
public class ResultVo implements Serializable {
    private String code;
    private String message;
    private String data;

    public ResultVo(){}

    public ResultVo(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResultVo getFailedResult(Integer code, String message) {
        return new ResultVo(code.toString(),message);
    }
}
