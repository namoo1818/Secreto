//package com.pjg.secreto.common.config;
//
//import com.pjg.secreto.common.handler.WebSocketHandler;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.socket.config.annotation.EnableWebSocket;
//import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
//import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
//
//@Configuration
//@EnableWebSocket
//@RequiredArgsConstructor
//public class WebSocketConfig implements WebSocketConfigurer {
//
//    @Autowired
//    private final WebSocketHandler chatHandler;
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(chatHandler, "/chat").setAllowedOrigins("*");
//    }
//
//}