package com.lvshen.demo;

import com.lvshen.demo.event.PayService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-8-28 14:26
 * @since JDK 1.8
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringEventTest {

    @Autowired
    private PayService payService;

    @Test
    public void testEvent() {
        String orderId = "5201314";
        payService.paySuccess(orderId);
    }
}
