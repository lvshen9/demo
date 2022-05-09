package com.lvshen.demo.authenticatedstreams.service;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lvshen.demo.arithmetic.snowflake.SnowFlakeGenerator;
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
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
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
public class ProcessConfigService {
    @Autowired
    private ConfigMapper configMapper;

    @Autowired
    private NodeMapper nodeMapper;

    @Autowired
    private Snowflake snowflake;

    @Transactional(rollbackFor = Exception.class)
    public void createConfig(ProcessParam param) {

        String code = String.valueOf(snowflake.nextId());
        param.setId(String.valueOf(snowflake.nextId()));
        param.setCode(code);
        param.setDeleted(Byte.valueOf("0"));
        param.setCreatedTime(new Date());
        param.setLevels(getLevels(param));
        ProcessConfig config = new ProcessConfig();
        BeanUtils.copyProperties(param, config);
        configMapper.insert(config);

        List<ProcessNode> processNodes = param.getProcessNodes();
        processNodes.forEach(processNode -> {
            processNode.setId(String.valueOf(snowflake.nextId()));
            processNode.setCode(code);
            nodeMapper.insert(processNode);
        });
    }

    private Byte getLevels(ProcessParam config) {
        return Byte.valueOf(String.valueOf(config.getProcessNodes().size()));
    }

    public ProcessConfig getByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        ProcessConfig config = new ProcessConfig();
        config.setCode(code);
        config.setDeleted(Byte.valueOf("0"));
        config.setStatus("1");

        return configMapper.selectOne(new QueryWrapper<>(config));

    }
}
