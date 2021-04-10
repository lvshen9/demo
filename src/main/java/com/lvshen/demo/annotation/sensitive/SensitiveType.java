package com.lvshen.demo.annotation.sensitive;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2021/4/10 11:23
 * @since JDK 1.8
 */
public enum SensitiveType {
    /**
     * 中文名
     */
    CHINESE_NAME,

    /**
     * 身份证号
     */
    ID_CARD,
    /**
     * 座机号
     */
    FIXED_PHONE,
    /**
     * 手机号
     */
    MOBILE_PHONE,
    /**
     * 地址
     */
    ADDRESS,
    /**
     * 电子邮件
     */
    EMAIL,
    /**
     * 银行卡
     */
    BANK_CARD,
}
