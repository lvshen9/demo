package com.lvshen.demo.member.entity;

import com.lvshen.demo.annotation.BeanClassTest;
import com.lvshen.demo.annotation.NeedSetValue;
import com.lvshen.demo.annotation.export.ExportFiled;
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

    private static final long serialVersionUID = 1L;

    @ExportFiled(number = "1",name = "序号ID")
    private String id;

    @ExportFiled(number = "2",name = "姓名")
    private String name;

    @ExportFiled(number = "3",name = "成绩")
    private Integer code;

    @ExportFiled(number = "4",name = "备注说明")
    @Transient
    @NeedSetValue(beanClass = BeanClassTest.class, param = "code", method = "getMemberByCode", targetFiled = "name")
    private String annotationParam;

    public void viewMember() {
        System.out.printf("Member测试！！！");
    }
}
