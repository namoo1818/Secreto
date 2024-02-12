//package com.pjg.secreto.chatting.command.controller;
//
//import com.pjg.secreto.common.response.SuccessResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RequiredArgsConstructor
//@RestController
//public class ChatCommandController {
//    @MessageMapping("/message")
//    public ResponseEntity<?> saveMessage(){
//        saveMessage();
//        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "개인 채팅 내역 저장 성공"));
//    }
//
//    @MessageMapping("/group_message")
//    public ResponseEntity<?> saveGroupMessage(){
//        saveGroupMessage();
//        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "개인 채팅 내역 저장 성공"));
//    }
//}
