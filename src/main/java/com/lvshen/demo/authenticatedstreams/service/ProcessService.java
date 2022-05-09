package com.lvshen.demo.authenticatedstreams.service;

import cn.hutool.core.lang.Assert;
import com.lvshen.demo.authenticatedstreams.entity.ProcessConfig;
import com.lvshen.demo.authenticatedstreams.entity.ProcessNode;
import com.lvshen.demo.authenticatedstreams.entity.ProcessRecord;
import com.lvshen.demo.authenticatedstreams.mapper.ProcessMapper;
import com.lvshen.demo.authenticatedstreams.praram.ProcessCreateParam;
import com.lvshen.demo.authenticatedstreams.praram.ProcessResult;
import com.lvshen.demo.catchexception.BusinessException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-5-9 11:21
 * @since JDK 1.8
 */
@Service
public class ProcessService {

    @Autowired
    private ProcessMapper processMapper;
    @Autowired
    private ProcessConfigService processConfigService;
    @Autowired
    private ProcessNodeService processNodeService;
    @Autowired
    private ProcessStrategyApplicationService processStrategyApplicationService;

    private String DEFAUL_ACCOUNT = "lvshen_pro";

    /**
     * 提交进入审核流程
     *
     * @param createParam
     * @return 主键id
     */
    public ProcessResult submitProcess(ProcessCreateParam createParam) {
        //校验并返回入参
        Pair<String, String> pair = validateParamAndReturn(createParam);
        String code = pair.getFirst();
        String sheetNo = pair.getSecond();
        ProcessRecord process = new ProcessRecord();
        BeanUtils.copyProperties(createParam, process);
        process.unDeleted();
        //初始化层级：1
        process.initLevel();

        //设置审核次数
        byte currentTimes = validateConfigAndReturnCurrentTimes(code, sheetNo);
        process.setTimes(currentTimes);
        //设置审核人
        String currentApproveMan = getCurrentApproveMan(code, 1);
        process.setApproveMan(currentApproveMan);
        //进入审核流程之前，创建的流程单状态默认为：审核中
        process.setStatus(Byte.valueOf("0"));

        processMapper.insert(process);

        //策略-提交进入流程的策略操作
        ProcessResult result = new ProcessResult();
        result.setCode(code);
        result.setSheetNo(sheetNo);
        result.setNextProcessId(String.valueOf(process.getId()));
        result.setApproveMan(getCurrentAccount());
        result.setNextApproveMan(currentApproveMan);
        result.setModuleCode(createParam.getModuleCode());
        processStrategyApplicationService.nextProcessOperation(result);

        return result;
    }

    /**
     * 更新最新审核节点，创建下一个审核节点
     *
     * @param createParam
     */
    public ProcessResult approveProcess(ProcessCreateParam createParam) {
        Pair<String, String> pair = validateParamAndReturn(createParam);
        String approveStatus = createParam.getApproveStatus();
        Assert.isTrue(!ApproveStatusEnum.IN.getValue().equals(approveStatus), "当前状态不能修改为【审核中】");

        String code = pair.getFirst();
        String sheetNo = pair.getSecond();
        ProcessRecord latestInfo = getLatestInfoByCodeAndSheetNo(code, sheetNo);
        Date currentDate = new Date();

        //1.更新当前节点
        updateLatestProcess(createParam, latestInfo, currentDate);

        //2.返回当前节点信息，创建下一节点
        //不能添加的情况：当前层级次数=最大允许层级次数；审核状态为：拒绝
        ProcessConfig processConfig = processConfigService.getByCode(code);
        Assert.notNull(processConfig, "未配置审核信息，审核流单号：【%s】", code);
        Byte allowTotalLevels = processConfig.getLevels();
        Byte lastLevel = latestInfo.getLevel();

        boolean equalsAllowLevelFlag = allowTotalLevels.byteValue() == lastLevel.byteValue();
        boolean failStatus = ApproveStatusEnum.FAIL.getValue().equals(approveStatus);
        ProcessResult result = ProcessResult.builder()
                .currentLevel(lastLevel)
                .processId(String.valueOf(latestInfo.getId()))
                .approveMan(latestInfo.getApproveMan())
                .status(approveStatus)
                .code(code)
                .sheetNo(sheetNo)
                .allowTotalLevels(allowTotalLevels)
                .currentTimes(latestInfo.getTimes())
                .allowTotalTimes(processConfig.getTimes())
                .moduleCode(processConfig.getModuleCode())
                .build();
        if (equalsAllowLevelFlag || failStatus) {
            result.setIsEndNode(ApplicationConstants.YES);
        } else {
            result.setIsEndNode(ApplicationConstants.NO);
            createNextProcess(createParam, latestInfo, currentDate, result);
        }
        //审批的后续操作
        processStrategyApplicationService.afterProcessOperation(result);
        return result;
    }

    private Pair<String, String> validateParamAndReturn(ProcessCreateParam createParam) {
        Assert.notNull(createParam, "参数不能为空!!!");
        String code = createParam.getCode();
        String sheetNo = createParam.getSheetNo();
        Assert.isTrue(StringUtils.isNotBlank(code) && StringUtils.isNotBlank(sheetNo),
                "addProcessBeforeApprove：流程单号【%s】或单据号【%s】不能为空", code, sheetNo);
        return Pair.of(code, sheetNo);
    }

    private byte validateConfigAndReturnCurrentTimes(String code, String sheetNo) {
        ProcessRecord latestProcessInfo = getLatestInfoByCodeAndSheetNo(code, sheetNo);
        if (latestProcessInfo == null) {
            return 1;
        } else {
            ProcessConfig processConfig = processConfigService.getByCode(code);
            Assert.notNull(processConfig, "未配置审核信息，审核流单号：【%s】", code);
            Byte status = latestProcessInfo.getStatus();
            Assert.isTrue(latestProcessInfo == null || ApproveStatusEnum.FAIL.getValue().equals(String.valueOf(status))
                    , "该流程在审核中或者已审核通过，不能再次发起审核");
            Byte allowTotalTimes = processConfig.getTimes();
            Byte lastTimes = latestProcessInfo.getTimes();
            if (allowTotalTimes.byteValue() == lastTimes.byteValue()) {
                throw new BusinessException(String.format("允许再次审核的次数【%s】已用完，上次为第【%s】次审核", allowTotalTimes.toString(), lastTimes.toString()));
            }
            return (byte) (lastTimes + 1);

        }

    }

    public ProcessRecord getLatestInfoByCodeAndSheetNo(String code, String number) {
        return processMapper.getLatestInfoByCodeAndSheetNo(code, number);
    }

    private String getCurrentApproveMan(String flowCode, int level) {
        ProcessNode node = processNodeService.getByCodeNameLevel(flowCode, Byte.valueOf(String.valueOf(level)));
        Assert.isTrue(node != null && node.getApproveMan() != null,
                "当前层级审核人不存在");
        String approveMan = node.getApproveMan();
        return String.valueOf(approveMan);
    }

    /**
     * 当前登录用户账户，这里是模拟功能
     *
     * @return
     */
    private String getCurrentAccount() {
        return this.DEFAUL_ACCOUNT;
    }

    public void setCurrentAccount(String account) {
        this.DEFAUL_ACCOUNT = account;
    }

    /**
     * 通过账号获取 用户名称，这里同样是模拟名称
     *
     * @param account
     * @return
     */
    private String getNameByAccountOrId(String account) {
        return "Lvshen的技术小屋".concat(account);
    }

    private void updateLatestProcess(ProcessCreateParam createParam, ProcessRecord latestInfo, Date currentDate) {
        Byte latestStatus = latestInfo.getStatus();
        String approveMan = latestInfo.getApproveMan();
        String currentAccount = getCurrentAccount();
        Assert.isTrue(StringUtils.isNotBlank(currentAccount), "当前登录人信息为空");
        Assert.isTrue(currentAccount.equals(approveMan), String.format("当前登录人【%s】不是当前环节审批人【%s】"
                , getNameByAccountOrId(currentAccount), getNameByAccountOrId(approveMan)));
        String approveStatus = createParam.getApproveStatus();
        Assert.isTrue(ApproveStatusEnum.IN.getValue().equals(String.valueOf(latestStatus))
                , "该节点已审批");
        ProcessRecord updateLatest = new ProcessRecord();
        BeanUtils.copyProperties(createParam, updateLatest);
        updateLatest.setId(latestInfo.getId());
        updateLatest.setApproveTime(currentDate);
        updateLatest.setStatus(Byte.valueOf(approveStatus));
        updateLatest.setUpdatedTime(currentDate);
        processMapper.updateById(updateLatest);
    }

    private void createNextProcess(ProcessCreateParam createParam, ProcessRecord latestInfo, Date currentDate, ProcessResult result) {
        ProcessRecord createNext = new ProcessRecord();
        BeanUtils.copyProperties(createParam, createNext);
        createNext.unDeleted();
        String flowCode = createParam.getCode();
        Byte latestLevel = latestInfo.getLevel();
        byte nextLevel = (byte) (latestLevel + 1);
        createNext.setLevel(nextLevel);
        createNext.setTimes(latestInfo.getTimes());
        String nextApproveMan = getCurrentApproveMan(flowCode, nextLevel);
        createNext.setApproveMan(nextApproveMan);
        createNext.setStatus(Byte.valueOf(ApproveStatusEnum.IN.getValue()));
        createNext.setCreatedBy(getCurrentAccount());
        createNext.setCreatedTime(currentDate);
        processMapper.insert(createNext);

        result.setNextProcessId(String.valueOf(createNext.getId()));
        result.setNextApproveMan(nextApproveMan);

        //策略模式，下个审批人需要做的事情
        processStrategyApplicationService.nextProcessOperation(result);
    }
}
