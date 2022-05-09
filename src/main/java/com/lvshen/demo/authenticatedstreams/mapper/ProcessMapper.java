package com.lvshen.demo.authenticatedstreams.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lvshen.demo.authenticatedstreams.entity.ProcessConfig;
import com.lvshen.demo.authenticatedstreams.entity.ProcessRecord;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-5-7 11:20
 * @since JDK 1.8
 */
@Repository
public interface ProcessMapper extends BaseMapper<ProcessRecord> {

    @Select("select * from as_process where code = #{code} and sheet_no = #{number} and deleted = 0 order by id desc limit 1")
    @ResultMap("BaseResultMap")
    ProcessRecord getLatestInfoByCodeAndSheetNo(@Param("code") String code, @Param("number") String number);
}
