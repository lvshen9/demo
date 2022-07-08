package com.lvshen.demo.websocket;

import com.alibaba.fastjson.JSONObject;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-6-30 9:03
 * @since JDK 1.8
 */
public class Main {
    public static void main(String[] args){
        WebSocketVO webSocketVO = new WebSocketVO();
        webSocketVO.setUserId(57);
        webSocketVO.setTalentsId(103602);
        WebSocketSever.sendMessageByUser(57, JSONObject.toJSONString(webSocketVO));
    }
}
