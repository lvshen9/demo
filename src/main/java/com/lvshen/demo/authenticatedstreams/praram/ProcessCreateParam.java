package com.lvshen.demo.authenticatedstreams.praram;

import lombok.Data;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-5-9 11:23
 * @since JDK 1.8
 */
@Data
public class ProcessCreateParam {
    /**
     * code:审核流编号，必填
     */
    private String code;

    /**
     * sheet_no:单据号，必填
     */
    private String sheetNo;

    /**
     * memo:审核意见
     */
    private String memo;

    /**
     * 审核状态；0=审核中，1=通过，2=拒绝，当此字段不传值时，默认取审核中0
     */
    private String approveStatus;

    /**
     * 模块编号：非必填
     */
    private String moduleCode;
}
