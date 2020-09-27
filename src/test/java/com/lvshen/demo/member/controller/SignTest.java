package com.lvshen.demo.member.controller;

import com.lvshen.demo.member.service.SignService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2020/3/7 16:31
 * @since JDK 1.8
 */

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class SignTest {

    @Autowired
    private SignService signService;

    @Test
    public void test() {
        String userId = "1";
        int sign = signService.createSign(userId);
        System.out.println(sign);
    }
}