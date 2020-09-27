package com.lvshen.demo.design.builder;

import cn.hutool.core.lang.Console;
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

    public void show(){
        String partA = getPartA();
        String partB = getPartB();
        String partC = getPartC();
        System.out.println("这可能是东半球最好的产品...");
        Console.log("组成结构:{},{},{}",partA,partB,partC);
    }
}
