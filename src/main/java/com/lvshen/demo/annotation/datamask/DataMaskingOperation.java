package com.lvshen.demo.annotation.datamask;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-7-22 9:15
 * @since JDK 1.8
 */
public interface DataMaskingOperation {
    String MASK_CHAR = "*";

    String mask(String content, String maskChar);
}
