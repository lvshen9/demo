package com.lvshen.demo.serializable;

import lombok.Data;

import java.io.Serializable;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-9-4 10:55
 * @since JDK 1.8
 */
@Data
public class UserInfo implements Serializable {
    //private static final long serialVersionUID = 1L;

    private String name;  //姓名
    private String hobby; //爱好
    private transient String address = "上海-浦东区";//地址
}
