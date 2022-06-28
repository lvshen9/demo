package com.lvshen.demo.logexception.dao;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.alibaba.excel.util.CollectionUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageParams;
import com.google.common.collect.ImmutableList;
import com.lvshen.demo.catchexception.BusinessExceptionAssert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lvshen
 * @Description api请求日志记录表
 * @date 2022/05/18
 */
@Service
@Slf4j
public class ApiLogService {
    @Autowired
    private ApiLogMapper apiLogMapper;
    @Autowired
    private Snowflake snowflake;



    /**
     * 创建 api请求日志记录表
     *
     * @param apiLog
     * @return 入库成功数目
     */
    @Transactional(value = "defaultTransactionManager", rollbackFor = Exception.class)
    public void addApiLog(ApiLog apiLog) {
        BusinessExceptionAssert.checkNotNull(apiLog, "参数不能为空!!!");
        apiLog.setId(String.valueOf(snowflake.nextId()));
        apiLog.unDeleted();
        apiLogMapper.insert(apiLog);
    }

    /**
     * 更新 api请求日志记录表
     *
     * @param apiLog
     * @return 更新成功数目
     */

    public int updateApiLog(ApiLog apiLog) {
        BusinessExceptionAssert.checkNotNull(apiLog, "参数不能为空!!!");
        apiLog.unDeleted();
        return apiLogMapper.updateById(apiLog);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateNormalStatus(String logId) {
        ApiLog apiLog = new ApiLog();
        apiLog.setId(logId);
        apiLog.setIsException("0");
        updateApiLog(apiLog);
    }

    /**
     * 逻辑删除，根据id删除 api请求日志记录表
     *
     * @param id
     * @return 更新成功数目
     */
    public int deleteLogicApiLog(String id) {
        ApiLog apiLog = new ApiLog();
        apiLog.setId(id);
        apiLog.hasDeleted();
        return apiLogMapper.updateById(apiLog);
    }

    /**
     * 通过id查询 api请求日志记录表
     *
     * @param id
     * @return api请求日志记录表信息
     */
    public ApiLog getApiLogById(String id) {
        ApiLog apiLog = apiLogMapper.selectById(id);
        if (apiLog != null) {
            String createdBy = apiLog.getCreatedBy();
            String nameByAccountOrId = "lvshen";
            apiLog.setCreatedBy(nameByAccountOrId);
        }
        return apiLogMapper.selectById(id);
    }

    /**
     * 分页 api请求日志记录表
     *
     * @param queryParam
     * @return 分页列表
     */
    public PageInfo<ApiLogVo> pageQueryApiLog(PageParams pageParams, ApiLogQueryParam queryParam) {

        List<ApiLogVo> apiLogVos = apiLogMapper.listApiLogVo(queryParam);
        PageInfo<ApiLogVo> voPageInfo = new PageInfo<>(apiLogVos);
        List<ApiLogVo> data = voPageInfo.getList();
        if (CollectionUtils.isEmpty(data)) {
            return new PageInfo<>(ImmutableList.of());
        }
        for (ApiLogVo vo : data) {
            String createdBy = vo.getCreatedBy();
            String name = "lvshen";
            vo.setCreatedByName(name);
        }
        return voPageInfo;
    }


    /**
     * 通过id列表批量查询 api请求日志记录表
     *
     * @param ids
     * @return list
     */
    public List<ApiLog> listApiLogByIds(List<String> ids) {
        List<ApiLog> entities = apiLogMapper.selectBatchIds(ids);
        return entities;
    }
}