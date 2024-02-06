package com.pjg.secreto.chatting.common.dto;


import lombok.Builder;
import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Builder
@Data
public class ChatDto {
    private Long chatNo;
    private String firstTime;
    private Set<WebSocketSession> sessions = new HashSet<>();
}

