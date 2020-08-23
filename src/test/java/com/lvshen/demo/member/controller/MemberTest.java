package com.lvshen.demo.member.controller;

import com.lvshen.demo.RedisSpringTest;
import com.lvshen.demo.arithmetic.snowflake.SnowFlakeGenerator;
import com.lvshen.demo.member.entity.Member;
import com.lvshen.demo.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.util.List;

import static com.lvshen.demo.RedisSpringTest.GET_NEXT_CODE;

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
class MemberTest {

    @Autowired
    private MemberController memberController;

    @Autowired
    private MemberService memberService;

    @Test
    void deleteById() {
    }

    @Test
    void update() {
    }

    @Test
    void insert() throws ParseException {
        Member member = new Member();
        String id = new SnowFlakeGenerator().nextId();
        member.setId(id);
        long code = new RedisSpringTest().getCode(GET_NEXT_CODE);
        member.setCode((int)code);
        member.setName("zhouzhou");

        Member insert = memberController.insert(member);
        log.info("数据插入成功: {}",insert);
    }

    @Test
    void listMember() {
        List<Member> members = memberController.listMember();
        log.info("members:{}",members);
    }

    @Test
    void listByName() {
        String name = "zhouzhou";
        List<Member> members = memberController.listByName(name);
        log.info("members:{}",members);
    }

    @Test
    void testCache() {
        String name = "lvshen99";
        List<Member> members = memberService.listByNameSelfCache(name);
        log.info("members:{}",members);
    }
}