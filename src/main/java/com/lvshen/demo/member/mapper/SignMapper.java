package com.lvshen.demo.member.mapper;


import com.lvshen.demo.member.entity.Sign;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

/**
* Created by Mybatis Generator on 2020/09/15
*/
@Mapper
public interface SignMapper {
    int insert(Sign record);

    int insertSelective(Sign record);
    
    @Select("select max(sign_date) from sign where user_id = #{userId}")
    Date getMaxDateByUserId(@Param("userId") String userId);
}