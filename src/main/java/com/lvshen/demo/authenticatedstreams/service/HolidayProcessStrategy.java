package com.lvshen.demo.authenticatedstreams.service;

import com.lvshen.demo.authenticatedstreams.praram.ProcessResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.lvshen.demo.authenticatedstreams.service.ModuleTypeEnum.HOLIDAY;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-5-9 14:00
 * @since JDK 1.8
 */
@Component
@Slf4j
public class HolidayProcessStrategy implements ProcessStrategy {
    @Override
    public void afterProcessOperation(ProcessResult result) {
        //策略莫模式，比如邮件通知申请人 流程审批结果

        log.info("流程【{}】审核结束，审核结果【{}】", result.getProcessId(), result.getStatus());
    }

    @Override
    public void nextProcessOperation(ProcessResult result) {
        //策略模式，比如通知下一环节审批人等
    }

    @Override
    public String getModuleTypeStr() {
        return HOLIDAY.getValue();
    }
}
