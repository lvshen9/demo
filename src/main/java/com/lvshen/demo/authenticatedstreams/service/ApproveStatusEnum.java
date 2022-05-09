package com.lvshen.demo.authenticatedstreams.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-5-9 14:14
 * @since JDK 1.8
 */
@Getter
@AllArgsConstructor
public enum ApproveStatusEnum {
    IN("审核中", "0"),
    PASS("通过", "1"),
    FAIL("不通过", "2");

    private String desc;
    private String value;
}
