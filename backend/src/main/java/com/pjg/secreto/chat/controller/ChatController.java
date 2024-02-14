package com.pjg.secreto.chat.controller;

import com.pjg.secreto.chat.dto.ChatMessageDto;
import com.pjg.secreto.chat.dto.ShowChatUserListResponseDto;
import com.pjg.secreto.chat.service.ChatService;
import com.pjg.secreto.common.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/chatting/{chatRoomNo}")    // 메서드 호출 경로
    @SendTo("/topic/{chatRoomNo}")    // 구독하고 있는 장소로 메시지 전송
    public ResponseEntity<?> chatting(@DestinationVariable Long chatRoomNo, ChatMessageDto chatMessageDto) {

        chatService.chatting(chatMessageDto);

        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "채팅 성공", chatMessageDto.getMessage()));
    }

    @GetMapping(value="/chat/chat_user/{roomNo}")
    public ResponseEntity<?> showChatUserList(@PathVariable("roomNo") Long roomNo){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        Long userNo = com.pjg.secreto.common.Util.AuthUtils.getAuthenticatedUserId();

        List<ShowChatUserListResponseDto> result = chatService.showChatUserList(userNo, roomNo);

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "채팅방 별 유저 리스트를 조회하였습니다.", result));
    }

//    @GetMapping(value="/chat/{roomNo}/chat_room/{chatNo}")
//    public ResponseEntity<?> showChatList(@PathVariable("roomNo") Long roomNo){
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
//
//        Long userNo = com.pjg.secreto.common.Util.AuthUtils.getAuthenticatedUserId();
//
//        List<ShowChatUserListResponseDto> result = chatService.showChatUserList(userNo, roomNo);
//
//        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "채팅방 별 유저 리스트를 조회하였습니다.", result));
//    }

}