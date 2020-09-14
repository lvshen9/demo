package com.lvshen.demo.jsoup;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-9-7 17:17
 * @since JDK 1.8
 */
@Data
@Builder
public class ProductInfo {

    //来源网站
    private String sourceUrl;

    //采集时间
    private Date collectDate;

    //标题
    private String title;

    //价格
    private BigDecimal price;

    //工作小时
    private Long workTime;

    //出厂时间
    private String deliveryDate;

    //排放标准
    private String dischargeStandard;

    //品牌
    private String brand;

    //汽车底盘
    private String automobileChassis;

    //还款情况
    private String repayment;

    //过户情况
    private String transferStatus;

    //有无保险
    private String insurance;

    //臂架长度
    private String boomLength;

    //泵送方量
    private String pumpingVolume;

    //行驶证
    private String drivingLicense;
    //设备来源
    private String equipmentSource;

    //描述
    private String description;

    //图片
    private String imgUrl;

    //添加id
    private String addId;

    private String code;

}
