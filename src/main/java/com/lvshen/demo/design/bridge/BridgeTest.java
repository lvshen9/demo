package com.lvshen.demo.design.bridge;

/**
 * Description：桥接模式
 *
 * @author yuange
 * @version 1.0
 * @date: 2020/1/20 10:21
 * @since JDK 1.8
 */
public class BridgeTest {
    public static void main(String[] args){
        Implementor implementorA = new ConcreteImplementorA();
        RefinedAbstraction refinedAbstraction = new RefinedAbstraction(implementorA);
        refinedAbstraction.operation();
    }
}
