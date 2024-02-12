package com.pjg.secreto.chatting.stomp.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class ChatResponse {
    private String sender;
    private String message;
    private LocalDateTime sendAt;
    private Boolean readYn;
}
