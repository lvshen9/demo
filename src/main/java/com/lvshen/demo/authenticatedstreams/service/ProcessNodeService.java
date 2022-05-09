package com.lvshen.demo.authenticatedstreams.service;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lvshen.demo.authenticatedstreams.entity.ProcessConfig;
import com.lvshen.demo.authenticatedstreams.entity.ProcessNode;
import com.lvshen.demo.authenticatedstreams.mapper.ConfigMapper;
import com.lvshen.demo.authenticatedstreams.mapper.NodeMapper;
import com.lvshen.demo.authenticatedstreams.praram.ProcessParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-5-7 11:25
 * @since JDK 1.8
 */
@Service
public class ProcessNodeService {
    @Autowired
    private NodeMapper nodeMapper;

    public ProcessNode getByCodeNameLevel(String code, Byte level) {
        ProcessNode node = new ProcessNode();
        node.setCode(code);
        node.setLevel(level);
        return nodeMapper.selectOne(new QueryWrapper<>(node));
    }

}
