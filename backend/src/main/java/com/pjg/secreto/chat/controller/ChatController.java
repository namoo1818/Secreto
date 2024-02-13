package com.pjg.secreto.chat.controller;

import com.pjg.secreto.chat.dto.ChatMessageDto;
import com.pjg.secreto.chat.service.ChatService;
import com.pjg.secreto.common.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/chatting")    // 보내는 경로
    @SendTo("/topic/{chatRoomNo}")    // 소켓 연결
    public ResponseEntity<?> chatting(ChatMessageDto chatMessageDto) {

        chatService.chatting(chatMessageDto);

        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "채팅 성공", chatMessageDto.getMessage()));
    }

}
