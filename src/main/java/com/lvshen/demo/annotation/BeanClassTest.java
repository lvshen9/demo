package com.lvshen.demo.annotation;

import com.google.common.collect.Lists;
import com.lvshen.demo.member.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/3/16 14:15
 * @since JDK 1.8
 */
@Component
@Slf4j
public class BeanClassTest {

    public Member getMemberByCode(Integer code) {
        Member member = new Member();
        member.setCode(code);
        member.setName("lvshen" + code);

        return member;
    }

    @NeedSetValueField
    public List<Member> listMemberVO(List<Integer> codes) {
        List<Member> members = Lists.newArrayList();
        for (Integer code : codes) {
            Member memberByCode = getMemberByCode(code);
            members.add(memberByCode);
        }
        return members;
    }

    /*@PostConstruct
    public void init() {
        List<Integer> codes = Arrays.asList(100, 200);
        List<Member> memberVOS = listMemberVO(codes);
        log.info("voList:{}", memberVOS);
    }*/

}
