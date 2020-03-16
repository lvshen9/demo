package com.lvshen.demo;

import com.lvshen.demo.annotation.BeanClassTest;
import com.lvshen.demo.annotation.MemberVO;
import com.lvshen.demo.distributelock.ZkWatcherService;
import com.lvshen.demo.distributelock.order.OrderServiceImplWithDisLock;
import com.lvshen.demo.member.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/12/26 14:52
 * @since JDK 1.8
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class AnnotationTest {

    @Autowired
    private BeanClassTest beanClassTest;

    @Test
    public void test() {
        int code = 100;
        Member memberByCode = beanClassTest.getMemberByCode(code);

        log.info("member:{}", memberByCode);
    }

    /**
     * 自定义注解控制
     */
    @Test
    public void testAnnotation() {
        List<Integer> codes = Arrays.asList(100, 200);
        List<Member> memberVOS = beanClassTest.listMemberVO(codes);
        log.info("voList:{}", memberVOS);

    }
}
