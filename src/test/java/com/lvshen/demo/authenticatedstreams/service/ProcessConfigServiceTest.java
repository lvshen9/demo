package com.lvshen.demo.authenticatedstreams.service;

import cn.hutool.core.lang.Console;
import com.google.common.collect.Lists;
import com.lvshen.demo.authenticatedstreams.entity.ProcessConfig;
import com.lvshen.demo.authenticatedstreams.entity.ProcessNode;
import com.lvshen.demo.authenticatedstreams.praram.ProcessCreateParam;
import com.lvshen.demo.authenticatedstreams.praram.ProcessParam;
import com.lvshen.demo.authenticatedstreams.praram.ProcessResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static com.lvshen.demo.authenticatedstreams.service.ModuleTypeEnum.HOLIDAY;


@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
//@MapperScan(basePackages = {"com.lvshen.demo.authenticatedstreams.mapper"})
public class ProcessConfigServiceTest {


    @Autowired
    private ProcessConfigService processConfigService;
    @Autowired
    private ProcessService processService;

    @Test
    public void testCreateConfig() {
        // Setup
        ProcessParam param = new ProcessParam();
        param.setModuleCode("请假流程");
        param.setTimes((byte) 1);
        param.setStatus("1");
        param.setCreatedBy("lvshen");
        param.setCreatedTime(new GregorianCalendar(2022, Calendar.JANUARY, 1).getTime());


        List<ProcessNode> processNodes = Lists.newArrayList();
        for (int count = 1; count < 5; count++) {
            ProcessNode processNode = new ProcessNode();
            processNode.setApproveMan("lvshen".concat(String.valueOf(count)));
            processNode.setLevel((byte) count);
            processNode.setLevelDesc("lvshen测试".concat(String.valueOf(count)));
            processNodes.add(processNode);
        }
        param.setProcessNodes(processNodes);
        // Run the test
        processConfigService.createConfig(param);

        Console.log("test is over");

    }

    @Test
    public void testSubmit() {
        ProcessCreateParam createParam = new ProcessCreateParam();
        createParam.setCode("1523503259724156928");
        createParam.setSheetNo("A20220413004");
        createParam.setModuleCode(HOLIDAY.getValue());
        ProcessResult processResult = processService.submitProcess(createParam);
        Console.log("test is over");
    }

    @Test
    public void testApprove() {
        processService.setCurrentAccount("lvshen4");
        ProcessCreateParam createParam = new ProcessCreateParam();
        createParam.setCode("1523503259724156928");
        createParam.setSheetNo("A20220413004");
        createParam.setMemo("终极测试-Lvshen的技术小屋");
        createParam.setApproveStatus(ApproveStatusEnum.PASS.getValue());
        ProcessResult processResult = processService.approveProcess(createParam);
        Console.log("test is over");
    }
}
