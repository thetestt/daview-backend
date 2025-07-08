package com.daview.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * 통합 WebSocket 설정 (기존 채팅 + Care Pulse)
 * 기존 채팅 시스템을 위한 기본 설정 + Care Pulse 확장
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 숲: 메시지 브로커 설정으로 pub/sub 패턴 구현
     * 나무: /topic/pulse-alerts 구독자에게 위험도 알림을 브로드캐스트
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 기존 채팅 시스템 (/sub) + Care Pulse (/topic) 지원
        config.enableSimpleBroker("/sub", "/topic");
        
        // 클라이언트에서 서버로 메시지 전송 시 사용할 prefix (팀원 기존 설정에 맞춤)
        config.setApplicationDestinationPrefixes("/pub");
        
        // 사용자별 개인 알림을 위한 prefix 설정
        config.setUserDestinationPrefix("/user");
    }

    /**
     * 숲: WebSocket 연결 엔드포인트 등록
     * 나무: /ws-pulse 경로로 STOMP 클라이언트 연결, CORS 허용
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 기존 채팅 시스템용 엔드포인트
        registry.addEndpoint("/ws-chat")
                .setAllowedOriginPatterns("http://localhost:3000", "http://localhost:5173")
                .withSockJS();
        
        // Care Pulse용 엔드포인트
        registry.addEndpoint("/ws-pulse")
                .setAllowedOriginPatterns("http://localhost:3000", "http://localhost:5173")
                .withSockJS();
    }
}
