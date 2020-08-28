package com.lvshen.demo.qr;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;



/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/9/5 14:22
 * @since JDK 1.8
 */



@Service
public class QrService {

    /*@Resource
    private RestTemplate restTemplate;
     */
    public void createQr(JSONObject param, String token) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<JSONObject> re = restTemplate.postForEntity("https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + token, param, JSONObject.class);

    }
}

