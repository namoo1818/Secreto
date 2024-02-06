package com.pjg.secreto.chatting.common.dto;

import com.pjg.secreto.chatting.common.entity.Chat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class ChatMessageRequestDto {
    private Chat chat;
    private String sender;
    private String message;

}
