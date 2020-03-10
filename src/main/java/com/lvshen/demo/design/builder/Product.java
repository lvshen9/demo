package com.lvshen.demo.design.builder;

import lombok.Data;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/12/31 9:25
 * @since JDK 1.8
 */
@Data
public class Product {
    private String partA;
    private String partB;
    private String partC;

    public void show(){}
}
