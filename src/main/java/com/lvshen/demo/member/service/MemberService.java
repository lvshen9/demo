package com.lvshen.demo.member.service;

import com.lvshen.demo.member.entity.Member;
import com.lvshen.demo.member.mapper.MemberMapper;
import com.lvshen.demo.redis.cache.CustomizeCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @CustomizeCache(value = "member", key = "#name")
    public List<Member> listByNameSelfCache(String name) {
        return memberMapper.listByName(name);
    }

    public List<Member> listMember(){
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


}
