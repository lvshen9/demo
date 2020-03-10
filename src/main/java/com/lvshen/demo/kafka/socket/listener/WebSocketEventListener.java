package com.lvshen.demo.kafka.socket.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/12/6 16:22
 * @since JDK 1.8
 */
@Component
@Slf4j
public class WebSocketEventListener {

	@EventListener
	public void handleWebSocketConnectListener(SessionConnectedEvent event) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

		Principal user = headerAccessor.getUser();

		log.info("Received a new web socket connection:{}", user);
	}

	@EventListener
	public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

		Principal user = headerAccessor.getUser();

		log.info("User Disconnected :{}", user);
	}
}
