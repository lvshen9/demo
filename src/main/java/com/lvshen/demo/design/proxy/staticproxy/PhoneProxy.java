package com.lvshen.demo.design.proxy.staticproxy;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-8-26 16:08
 * @since JDK 1.8
 */
public class PhoneProxy implements IPhone{
    private IPhone phone;
    public PhoneProxy(){
        this.phone = new Phone();
    }
    @Override
    public void call() {
        System.out.println("开启了录音...");
        phone.call();
    }
}
