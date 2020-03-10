package com.lvshen.demo.kafka.socket.controller;

import com.lvshen.demo.kafka.socket.consts.GlobalConsts;
import com.lvshen.demo.kafka.socket.model.ClientMessage;
import com.lvshen.demo.kafka.socket.model.ServerMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;


/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author
 * @date 2019/9/24 7:03 PM
 */
@Controller
public class GreetingController {

    @MessageMapping(GlobalConsts.HELLO_MAPPING)
    @SendTo(GlobalConsts.TOPIC)
    public ServerMessage greeting(ClientMessage message) throws Exception {
        // 模拟延时，以便测试客户端是否在异步工作
        Thread.sleep(1000);
        return new ServerMessage("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }
}
