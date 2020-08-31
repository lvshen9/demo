package com.lvshen.demo.exportTest;

import com.lvshen.demo.annotation.export.ExportExcel;
import com.lvshen.demo.member.entity.Member;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-8-31 15:14
 * @since JDK 1.8
 */

public class Test {
    @ExportExcel(beanClass = Member.class)
    private List<Member> listMember() {
        return Stream.of(
                new Member("1", "Lvshen", 10, "订阅号：Lvshen的技术小屋"),
                new Member("2", "Lvshen2", 20, "头条号：Lvshen的技术小屋"),
                new Member("3", "Lvshen3", 30, "知乎：Lvshen"),
                new Member("4", "Lvshen4", 10, "CSDN：Lvshen")
        ).collect(Collectors.toList());
    }

    @org.junit.Test
    public void test(){
        List<Member> members = listMember();
        System.out.println(members);
    }


}
