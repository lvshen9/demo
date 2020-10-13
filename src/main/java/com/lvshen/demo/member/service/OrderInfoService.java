package com.lvshen.demo.member.service;

import com.lvshen.demo.annotation.NeedSetValueField;
import com.lvshen.demo.member.constant.OrderStatusEnum;
import com.lvshen.demo.member.convert.OrderConverter;
import com.lvshen.demo.member.entity.Member;
import com.lvshen.demo.member.entity.Order;
import com.lvshen.demo.member.entity.vo.OrderVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/9/22 20:45
 * @since JDK 1.8
 */
@Component
@Slf4j
public class OrderInfoService {

    @Autowired
    private MemberService memberService;

    private List<Order> listOrder() {
        return Stream.of(
                new Order("001", "10", OrderStatusEnum.DELIVERY.name()),
                new Order("002", "11",OrderStatusEnum.DELIVERY.name()),
                new Order("003", "12",OrderStatusEnum.DELIVERY.name()),
                new Order("004", "15",OrderStatusEnum.DELIVERY.name())
        ).collect(Collectors.toList());
    }

    public List<OrderVo> listOrderVo() {
        //1.从数据库中查询Order
        List<Order> orderList = this.listOrder();
        log.info("数据库中的数据orderList:{}",orderList);
        //list之间的转换
        List<OrderVo> orderVoList = OrderConverter.INSTANCE.listentity2Vo(orderList);

        //2.用户服务中获取用户名
        orderVoList.forEach(orderVo -> {
            String memberId = orderVo.getMemberId();
            Member member = memberService.getById(memberId);
            String name = Optional.ofNullable(member).orElseGet(Member::new).getName();
            orderVo.setMemberName(name);
        });
        log.info("展示的数据orderVoList: {}",orderVoList);
        return orderVoList;
    }

    @NeedSetValueField
    public List<OrderVo> listOrderVoByAnnotation() {
        //1.从数据库中查询Order
        List<Order> orderList = this.listOrder();
        log.info("数据库中的数据orderList:{}",orderList);
        //list之间的转换
        return OrderConverter.INSTANCE.listentity2Vo(orderList);
    }

    /**
     * 修改订单至发货
     * @return
     */
    public void updateOrderDelivery() {
        Order order = new Order();
        order.setId("1");
        order.setMemberId("001");
        order.buildDeliveryStatus();
        //修改方法

    }
}
