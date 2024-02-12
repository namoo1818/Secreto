package com.pjg.secreto.chatting.stomp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage{
    private String time;
    private String content;
}