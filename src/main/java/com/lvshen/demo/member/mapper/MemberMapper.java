package com.lvshen.demo.member.mapper;

import com.lvshen.demo.member.entity.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2020/3/7 15:54
 * @since JDK 1.8
 */
@Mapper
public interface MemberMapper {

    List<Member> listByName(String name);

    List<Member> listMember();

    int createMember(Member user);

    int deleteById(String id);

    int updateMember(Member user);

    Member getMemberById(String id);

}
