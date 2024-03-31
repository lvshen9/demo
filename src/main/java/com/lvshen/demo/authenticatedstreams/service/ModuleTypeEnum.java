package com.lvshen.demo.authenticatedstreams.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-5-9 14:32
 * @since JDK 1.8
 */
@Getter
@AllArgsConstructor
public enum ModuleTypeEnum {
    HOLIDAY("请假", "HOLIDAY"),
    /**
     * 默认模块
     */
    SRM("供应链管理系统", "SRM");

    private String desc;
    private String value;
}
