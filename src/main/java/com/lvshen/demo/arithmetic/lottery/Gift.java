package com.lvshen.demo.arithmetic.lottery;

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
public class Gift {

    private int id;         //奖品Id
    private String name;    //奖品名称
    private double prob;    //获奖概率

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getProb() {
        return prob;
    }
    public void setProb(double prob) {
        this.prob = prob;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
