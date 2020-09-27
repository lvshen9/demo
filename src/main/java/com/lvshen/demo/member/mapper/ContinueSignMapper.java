package com.lvshen.demo.member.mapper;


import com.lvshen.demo.member.entity.ContinueSign;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
* Created by Mybatis Generator on 2020/09/15
*/
@Mapper
public interface ContinueSignMapper {
    int insert(ContinueSign record);

    int insertSelective(ContinueSign record);

    //@Select("select * from continue_sign where user_id = #{userId}")
    List<ContinueSign> listByUserId(@Param("userId") String userId);

    @Update("update continue_sign set continue_counts = continue_counts + 1 where user_id = #{userId}")
    int addSignCountsById(@Param("userId") String userId);
}