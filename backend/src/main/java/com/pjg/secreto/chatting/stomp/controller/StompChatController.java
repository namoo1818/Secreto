package com.pjg.secreto.chatting.stomp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StompChatController {

//    private final ChatService chatService;
//
//    @MessageMapping("/chat/{roomNo}")
//    @SendTo("/sub/chat/{roomNo}")
//    public ChatResponse broadcasting(final ChatRequest request,
//                                     @DestinationVariable(value = "roomNo") final String chatRoomNo) {
//
//        log.info("{roomNo : {}, request : {}}", chatRoomNo, request);
//
//        return chatService.recordHistory(chatRoomNo, request);
//    }

}