package com.lvshen.demo.member.service;

import com.lvshen.demo.annotation.export.ExportExcel;
import com.lvshen.demo.member.entity.Member;
import com.lvshen.demo.member.mapper.MemberMapper;
import com.lvshen.demo.redis.cache.CustomizeCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2020/3/7 15:57
 * @since JDK 1.8
 */
@Service
public class MemberService {
    @Autowired
    private MemberMapper memberMapper;

    @Cacheable(value = "member",key = "#name")
    public List<Member> listByName(String name) {
       return memberMapper.listByName(name);
    }

    public Member getById(String id) {
        return memberMapper.getMemberById(id);
    }

    @CustomizeCache(value = "member", key = "#name",expireTimes = 3600)
    public List<Member> listByNameSelfCache(String name) {
        return memberMapper.listByName(name);
    }

    public List<Member> listMember(){
        Member member = memberMapper.listMember().get(0);
        String name = member.getName();
        System.out.println(name);       //测试
        return memberMapper.listMember();
    }

    @Transactional
    public Member createMember(Member user){
        memberMapper.createMember(user);
        return user;
    }

    @Transactional
    public int deleteById(String id) {
        return memberMapper.deleteById(id);
    }

    public int updateMember(Member user){
        return memberMapper.updateMember(user);
    }

    @ExportExcel(beanClass = Member.class)
    public List<Member> listMemberByAnnotation(HttpServletResponse response) {
        return Stream.of(
                new Member("1", "Lvshen", 10, "订阅号：Lvshen的技术小屋"),
                new Member("2", "Lvshen2", 20, "头条号：Lvshen的技术小屋"),
                new Member("3", "Lvshen3", 30, "知乎：Lvshen"),
                new Member("4", "Lvshen4", 10, "CSDN：Lvshen")
        ).collect(Collectors.toList());
    }


}
