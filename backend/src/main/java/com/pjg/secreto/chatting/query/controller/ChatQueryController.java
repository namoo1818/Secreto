package com.pjg.secreto.chatting.query.controller;

import com.pjg.secreto.common.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ChatQueryController {

    @GetMapping(value="/message/{chatNo}")
    public ResponseEntity<?> loadMessage(){
        loadMessage();
        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "개인 채팅 내역 불러오기 성공", null));
    }

    @GetMapping(value="/group_message/{groupChatNo}")
    public ResponseEntity<?> loadGroupMessage(){
        loadGroupMessage();
        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "단체 채팅 내역 불러오기 성공", null));
    }
}
