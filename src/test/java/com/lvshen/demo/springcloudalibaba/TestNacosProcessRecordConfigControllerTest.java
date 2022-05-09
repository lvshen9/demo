package com.lvshen.demo.springcloudalibaba;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2020/3/11 12:55
 * @since JDK 1.8
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
class TestNacosProcessRecordConfigControllerTest {

    @Autowired
    private TestNacosConfigController testNacosConfigController;
    @Test
    void get() {
        boolean b = testNacosConfigController.get();
        System.out.println("result:" + b);
    }
}