package com.pjg.secreto.chat.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatMessageDto {

    private String sender;
    private String message;
    private Boolean readYn;
    private Long chatNo;
    private LocalDateTime sendAt;
}