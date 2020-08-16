package com.lvshen.demo.arithmetic.lottery;

import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/7/16 9:51
 * @since JDK 1.8
 */
@Data
@ToString
public class Gift {

    private int id;         //奖品Id
    private String name;    //奖品名称
    private double prob;    //获奖概率

    /*@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }*/

}
