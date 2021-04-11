package com.lvshen.demo.annotation.sensitive;

import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2021/4/10 11:27
 * @since JDK 1.8
 */
@Component
public class SensitiveService {

    public UserInfo getUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setAge(23);
        userInfo.setIdCard("32133458888988771");
        userInfo.setMobile("13456782345");
        userInfo.setNativePlace("北京海定区");
        userInfo.setSex("男");
        userInfo.setUseId(1234567L);
        userInfo.setUseName("爱德华");
        userInfo.setUseNo("11");
        return userInfo;
    }

    public UserInfo handleData() {
        UserInfo userInfo = getUserInfo();
        String mobile = userInfo.getMobile();
        String idCard = userInfo.getIdCard();

        //将手机和身份证号处理
        //String afterMobile = ....
        //String afterIdCard = ....
        //userInfo.setMobile(afterMobile)
        //userInfo.setIdCard(afterIdCard)
        return userInfo;
    }
}
