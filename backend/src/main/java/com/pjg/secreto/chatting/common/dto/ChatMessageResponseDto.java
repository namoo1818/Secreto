package com.pjg.secreto.chatting.common.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ChatMessageResponseDto {
    private Long chatMessageNo;
    private Long chatNo;
    private String content;
    private String sendAt;
    private Boolean readYn;
    private String sender;
}
