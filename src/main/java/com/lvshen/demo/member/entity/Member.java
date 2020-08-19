package com.lvshen.demo.member.entity;

import com.lvshen.demo.annotation.BeanClassTest;
import com.lvshen.demo.annotation.NeedSetValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;

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
public class Member implements Serializable {

    private String id;
    private String name;
    private Integer code;

    @Transient
    @NeedSetValue(beanClass = BeanClassTest.class, param = "code", method = "getMemberByCode", targetFiled = "name")
    private String annotationParam;

    public void viewMember() {
        System.out.printf("Member测试！！！");
    }
}
