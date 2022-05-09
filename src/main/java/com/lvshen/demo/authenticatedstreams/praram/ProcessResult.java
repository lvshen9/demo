package com.lvshen.demo.authenticatedstreams.praram;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-5-9 13:53
 * @since JDK 1.8
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcessResult {
    /**
     * 流程id
     */
    private String processId;

    /**
     * 下一个流程id
     */
    private String nextProcessId;

    /**
     * 当前节点是否为最终节点
     */
    private String isEndNode;

    /**
     * 当前节点审核状态
     */
    private String status;

    /**
     * 当前节点审核层级
     */
    private Byte currentLevel;

    /**
     * 当前流程允许的最大层级
     */
    private Byte allowTotalLevels;

    /**
     * 当前审核轮次
     */
    private Byte currentTimes;

    /**
     * 当前流程允许的最大审核轮次
     */
    private Byte allowTotalTimes;

    /**
     * 当前审核人
     */
    private String approveMan;

    /**
     * 下一个审核人
     */
    private String nextApproveMan;

    /**
     * 流程模块编码
     */
    private String moduleCode;

    /**
     * 当前流程编号
     */
    private String code;

    /**
     * 业务单号
     */
    private String sheetNo;

    public boolean endNode() {
        return "1".equals(this.isEndNode);
    }
}
