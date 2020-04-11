package com.lvshen.demo.autoidempotent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/4/11 11:11
 * @since JDK 1.8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceException extends RuntimeException {
    private String code;
    private String msg;

    public ServiceException(String msg) {
        this.msg = msg;
    }


}
