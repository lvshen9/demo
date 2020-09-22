package com.lvshen.demo.member.convert;

import com.lvshen.demo.member.entity.Order;
import com.lvshen.demo.member.entity.vo.OrderVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/9/22 21:01
 * @since JDK 1.8
 */
@Mapper
public interface OrderConverter {
    OrderConverter INSTANCE = Mappers.getMapper(OrderConverter.class);

    OrderVo entity2Vo(Order order);

    List<OrderVo> listentity2Vo(List<Order> orders);
}
