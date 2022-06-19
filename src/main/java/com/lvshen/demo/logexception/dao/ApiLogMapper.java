package com.lvshen.demo.logexception.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lvshen
 * @Description api请求日志记录表
 * @date 2022/05/18
 */
@Repository
public interface ApiLogMapper extends BaseMapper<ApiLog> {

    List<ApiLogVo> listApiLogVo(@Param("param") ApiLogQueryParam param);
}
