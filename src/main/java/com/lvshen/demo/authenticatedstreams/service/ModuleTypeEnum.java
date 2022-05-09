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
    HOLIDAY("请假", "HOLIDAY");

    private String desc;
    private String value;
}
