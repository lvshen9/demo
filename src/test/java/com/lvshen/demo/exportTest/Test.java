package com.lvshen.demo.exportTest;

import com.lvshen.demo.member.entity.Member;
import com.lvshen.demo.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-8-31 15:14c
 * @since JDK 1.8
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class Test {
    @Autowired
    private MemberService memberService;

    @org.junit.Test
    public void test(){
        HttpServletResponse response = new MockHttpServletResponse();
        List<Member> members = memberService.listMemberByAnnotation(response);
        System.out.println(members);
    }


}
