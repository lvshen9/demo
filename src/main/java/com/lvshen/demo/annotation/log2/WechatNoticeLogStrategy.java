package com.lvshen.demo.annotation.log2;

import com.lvshen.demo.authenticatedstreams.service.ModuleTypeEnum;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2024/3/31 19:15
 * @since JDK 1.8
 */
public class WechatNoticeLogStrategy implements LogStrategy {
    @Override
    public void afterHandler(LogResult result) {
        //例如企微消息通知
    }

    @Override
    public String getModuleTypeStr() {
        return ModuleTypeEnum.SRM.getValue();
    }
}
