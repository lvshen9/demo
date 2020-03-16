package com.lvshen.demo.annotation;

import lombok.Data;

import java.io.Serializable;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/3/16 14:12
 * @since JDK 1.8
 */
@Data
public class MemberVO implements Serializable {
    private static final long serialVersionUID = -8821751371277937944L;

    @NeedSetValue(beanClass = BeanClassTest.class, param = "code", method = "getMemberByCode", targetFiled = "name")
    private String name;
    private int code;
}
