package com.lvshen.demo.annotation.sensitive;

import com.lvshen.demo.annotation.datamask.DataMasking;
import com.lvshen.demo.annotation.datamask.DataMaskingFunc;
import lombok.Data;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2021/4/10 11:19
 * @since JDK 1.8
 */
@Data
public class UserInfo {
    private Long useId;

    /**
     * 用户编号
     */
    @DataMasking(maskFunc = DataMaskingFunc.ALL_MASK)
    private String useNo;

    /**
     * 用户姓名
     */
    private String useName;

    /**
     * 用户手机号
     */
    @SensitiveInfo(SensitiveType.MOBILE_PHONE)
    private String mobile;

    /**
     * 用户性别
     */
    private String sex;

    /**
     * 用户年龄
     */
    private int age;

    /**
     * 用户籍贯
     */
    private String nativePlace;

    /**
     * 用户身份证
     */
    @SensitiveInfo(SensitiveType.ID_CARD)
    private String idCard;

}
