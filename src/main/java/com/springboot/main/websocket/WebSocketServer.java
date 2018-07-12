package com.springboot.main.websocket;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.Session;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class WebSocketServer extends TextWebSocketHandler{

	private static Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<WebSocketSession>());
	
	@Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        
		for (WebSocketSession s : sessions) {
			if(!s.equals(session))
				s.sendMessage(new TextMessage(message.getPayload()));
		}
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session)throws Exception{
		sessions.add(session);
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session,CloseStatus status)throws Exception{
		sessions.remove(session);
	}
     
     
}
