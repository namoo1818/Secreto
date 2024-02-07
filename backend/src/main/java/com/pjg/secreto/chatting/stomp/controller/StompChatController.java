package com.pjg.secreto.chatting.stomp.controller;

import com.pjg.secreto.chatting.stomp.dto.ChatRequest;
import com.pjg.secreto.chatting.stomp.dto.ChatResponse;
import com.pjg.secreto.chatting.stomp.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StompChatController {

    private final ChatService chatService;

    @MessageMapping("/chat/{roomNo}")
    @SendTo("/sub/chat/{roomNo}")
    public ChatResponse broadcasting(final ChatRequest request,
                                     @DestinationVariable(value = "roomNo") final String chatRoomNo) {

        log.info("{roomNo : {}, request : {}}", chatRoomNo, request);

        return null;
//        return chatService.recordHistory(chatRoomNo, request);
    }

}