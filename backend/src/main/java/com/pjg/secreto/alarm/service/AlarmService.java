//package com.pjg.secreto.alarm.service;
//
//import com.pjg.secreto.alarm.repository.EmitterRepository;
//import com.pjg.secreto.room.command.repository.RoomUserCommandRepository;
//import com.pjg.secreto.room.query.repository.RoomUserQueryRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
//
//import java.io.IOException;
//
//@RequiredArgsConstructor
//@Service
//public class AlarmService {
//
//    private final RoomUserQueryRepository roomUserQueryRepository;
//    private final EmitterRepository emitterRepository;
//
//    public SseEmitter subscribe(Long roomUserNo) {
//
//        SseEmitter emitter = createEmitter(roomUserNo);
//
//        sendToClient(roomUserNo, "roomUserNo : " + roomUserNo + "과의 알람 연결 성공", "sse 접속 성공");
//
//        return emitter;
//
//    }
//
//    private void sendToClient(Long roomUserNo, Object data, String comment) {
//
//        SseEmitter emitter = emitterRepository.get(roomUserNo);
//        if(emitter != null) {
//            try {
//                emitter.send(SseEmitter.event()
//                        .id(String.valueOf(roomUserNo))
//                        .name("sse")
//                        .data(data)
//                        .comment(comment));
//
//            } catch (IOException e) {
//
//            }
//        }
//
//    }
//
//}
