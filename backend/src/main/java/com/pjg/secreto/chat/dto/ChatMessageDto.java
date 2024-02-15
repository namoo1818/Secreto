package com.pjg.secreto.chat.dto;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class ChatMessageDto {

    private String sender;
    private String message;
    private Boolean readYn;
    private Long chatNo;
    private LocalDateTime sendAt;
    private Long senderId;
    private String profileUrl;
}
