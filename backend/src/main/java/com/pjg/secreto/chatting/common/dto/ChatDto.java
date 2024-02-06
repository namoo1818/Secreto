package com.pjg.secreto.chatting.common.dto;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ChatDto {
    private Long chatNo;
    private String firstTime;
}

