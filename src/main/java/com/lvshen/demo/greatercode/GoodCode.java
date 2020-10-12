package com.lvshen.demo.greatercode;

import cn.hutool.core.lang.Console;
import com.lvshen.demo.member.entity.Member;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/10/12 22:13
 * @since JDK 1.8
 */
public class GoodCode {
    @Test
    public void test1() {
        float speed = 60f;
        if (speed > 0f && speed <120f) {
            Console.log("你的车速正常！！！");
        }
        //--------------------
        if (isNormalSpeed(speed)) {
            Console.log("你的车速正常！！！");
        }
    }

    public void test2() {
        String idCardNum = "xxxxxxxxxxxx";
        if (idCardNum.length() == 18) {
            Console.log("合法的身份证号！！！");
        }

        //--------------------
        if (isIdCard(idCardNum)) {
            Console.log("合法的身份证号！！！");
        }
    }

    public void test3() {
        Member member = new Member();
        member.setName("Zhouzhou");
        //....其他代码
        String userName = "Zhouzhou";
        if (StringUtils.isNotBlank(member.getName()) && member.getName().equals(userName)) {
            Console.log("我的女朋友是：{}",userName);
        }

        //-------------
        if (member.isMyGirlFriend()) {
            Console.log("我的女朋友是：{}",userName);
        }
    }

    private static boolean isNormalSpeed(float speed) {
        return speed > 0f && speed < 120f;
    }

    private static boolean isIdCard(String idCardNumber) {
        return idCardNumber.length() == 18;
    }
}
