package com.lvshen.demo.member.entity.vo;

import com.lvshen.demo.annotation.NeedSetValue;
import com.lvshen.demo.member.entity.Order;
import com.lvshen.demo.member.service.MemberService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/9/22 20:43
 * @since JDK 1.8
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OrderVo extends Order {

    private String id;

    private String memberId;
    /**
     * 获取订单用户name
     */
    @NeedSetValue(beanClass = MemberService.class, param = "memberId", method = "getById", targetFiled = "name")
    private String memberName;

}
